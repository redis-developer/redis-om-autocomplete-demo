import React, { useState } from 'react';
import ReactDOM from 'react-dom';
import Alert from 'react-bootstrap/Alert';
import Autosuggest from 'react-autosuggest';
import AirportsAPI from './services/airports_api';

async function getSuggestions(value) {
  let suggestions = await AirportsAPI.getSuggestions(value);
  return suggestions;
}

function getSuggestionValue(suggestion) {
  return suggestion.string;
}

function renderSuggestion(suggestion) {
  let payload = JSON.parse(suggestion.payload);

  return (
    <div className='suggestion-content '>
      <div className='react-autosuggest__section-title'><strong>{suggestion.string}</strong></div>
      <div>
        <span><strong>{payload.code}</strong> - {payload.state}</span>
      </div>
    </div>
  );
}

class App extends React.Component {
  constructor() {
    super();

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
    let payload = JSON.parse(suggestion.payload);
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

ReactDOM.render(
  <App />,
  document.getElementById('react')
);