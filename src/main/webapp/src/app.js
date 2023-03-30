import React from 'react';
import {createRoot} from 'react-dom/client';
import Alert from 'react-bootstrap/Alert';
import Autosuggest from 'react-autosuggest';
import AirportsAPI from './services/airports_api';

async function getSuggestions(value) {
  return await AirportsAPI.getSuggestions(value);
}

function getSuggestionValue(suggestion) {
  return suggestion.value;
}

function renderSuggestion(suggestion) {
  let payload = suggestion.payload;

  return (
    <div className='suggestion-content '>
      <div className='react-autosuggest__section-title'><strong>{suggestion.value}</strong></div>
      <div>
        <span><strong>{payload.code}</strong> - {payload.state}</span>
      </div>
    </div>
  );
}

class App extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      value: '',
      selected: '',
      suggestions: [],
      showSelection: false
    };
  }

  onChange = (event, { newValue, method }) => {
    this.setState({
      value: newValue,
      showSelection: this.state.selected > 0,
    });
  };

  // Suggestion rerender when user types
  onSuggestionsFetchRequested = ({ value }) => {
    getSuggestions(value)
      .then(data => {
        this.setState({
          suggestions: data
        });
      })
  };

  onSuggestionsClearRequested = () => {
    this.setState({
      suggestions: []
    });
  };

  onSuggestionSelected = (event, { suggestion }) => {
    let payload = suggestion.payload;
    this.setState({
      selected: `${payload.code} (${payload.state})`,
      showSelection: true
    });
  };

  render() {
    const { value, suggestions } = this.state;
    const inputProps = {
      placeholder: "Search by Airport Name...",
      value,
      onChange: this.onChange,
      className: "form-control form-control-lg form-control-borderless"
    };

    return (
      <div>
        <Autosuggest
          suggestions={suggestions}
          onSuggestionsFetchRequested={this.onSuggestionsFetchRequested}
          onSuggestionsClearRequested={this.onSuggestionsClearRequested}
          onSuggestionSelected={this.onSuggestionSelected}
          getSuggestionValue={getSuggestionValue}
          renderSuggestion={renderSuggestion}
          inputProps={inputProps} />
        <br/>
        {this.state.showSelection && <Alert variant="success">{this.state.selected}</Alert>}
      </div>
    );
  }
}

const container = document.getElementById('react');
const root = createRoot(container);
root.render(<App />);