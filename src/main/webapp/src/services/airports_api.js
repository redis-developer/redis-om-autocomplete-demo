const BASE_URL = 'http://localhost:8080/airports';

const AirportsAPI = function () { };

AirportsAPI.parseResponse = async function (response) {
  const body = await response.text();
  const json = JSON.parse(body);

  return json;
};

AirportsAPI.getSuggestions = async function (query) {
  const requestOptions = {
    method: 'GET',
    redirect: 'follow'
  };

  let actionUrl = `${BASE_URL}/search/${encodeURIComponent(query)}`;

  const response = await fetch(actionUrl, requestOptions);

  return AirportsAPI.parseResponse(response);
}

export default AirportsAPI;