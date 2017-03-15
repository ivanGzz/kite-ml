'use strict';
angular.module('documentation', []).controller('documentationController', ['$scope', function ($scope) {
    var doc = [{
        verb: 'GET',
        path: '/',
        description: 'Shows this documentation',
        returns: 'text/html',
        implemented: true
    }, {
        verb: 'GET',
        path: '/sentence',
        description: 'Shows the front end for sentences',
        returns: 'text/html',
        implemented: true
    }, {
        verb: 'POST',
        path: '/sentence',
        description: 'Creates a new sentences in the database',
        returns: 'text/plain',
        payload: "{\n\tlang: 'en',\n\tcontent: 'This is a new sentence',\n\tsentiment: '0',\n\tquestion: false,\n\tcommon_ground: false\n}",
        implemented: true
    }, {
        verb: 'PUT',
        path: '/sentence',
        description: 'Updates an existing sentence in the database',
        returns: 'text/plain',
        payload: "{\n\tid: 1,\n\tsentiment: '0',\n\tquestion: false,\n\tcommon_ground: false\n}",
        implemented: true
    }, {
        verb: 'GET',
        path: '/sentence/random',
        description: 'Returns a random sentence that exists in the database',
        returns: 'application/json',
        json: "{\n\tid: 1,\n\tlang: 'en',\n\tcontent: 'This is a new sentence',\n\tsentiment: '0',\n\tquestion: false,\n\tcommon_ground: false\n}",
        implemented: true
    }, {
        verb: 'POST',
        path: '/sentence/common-ground',
        description: 'Rates a sentence',
        returns: 'application/json',
        payload: "{\n\tlanguage: 'en',\n\tsentence: 'Why not use the documentation?'\n}",
        json: "{\n\tcommon_ground: true,\n\tsentiment: 0\n}",
        implemented: true
    }, {
        verb: 'GET',
        path: '/sentence/train',
        description: 'Trains the neural network with the sentences in the database',
        returns: 'text/plain',
        implemented: false
    }, {
        verb: 'POST',
        path: '/sentence/text',
        description: 'Rates several sentences in a paragraph',
        returns: 'application/json',
        json: "{}",
        payload: "{}",
        implemented: false
    }, {
        verb: 'GET',
        path: '/user-competency',
        description: 'Shows the user competencies front end',
        returns: 'text/html',
        implemented: false
    }, {
        verb: 'POST',
        path: '/user-competency',
        description: 'Creates a new user competency in the database and return its rate',
        returns: 'text/plain',
        payload: "{\n\tproject_id: 1,\n\tuser_id: 1,\n\tcompetencies: [0, 1, 2, 3]\n}",
        implemented: true
    }, {
        verb: 'PUT',
        path: '/user-competency',
        description: 'Updates an existing user competency in the database and returns its new rate',
        returns: 'text/plain',
        payload: "{\n\tproject_id: 1,\n\tuser_id: 1,\n\tcompetencies: [3, 2, 1, 0]\n}",
        implemented: true
    }, {
        verb: 'GET',
        path: '/user-competency/:userid',
        description: 'Returns the user with userid id',
        returns: 'application/json',
        json: "{}",
        implemented: true
    }, {
        verb: 'GET',
        path: '/user-competency/random',
        description: 'Returns a random user competency that exists in the database',
        returns: 'application/json',
        json: "{}",
        implemented: true
    }, {
        verb: 'GET',
        path: '/user-competency/train',
        description: 'Trains the neural network with the user competencies in the database',
        returns: 'text/plain',
        implemented: true
    }, {
        verb: 'GET',
        path: '/user-competency/load',
        description: 'Loads the most recent neural network',
        returns: 'text/plain',
        implemented: false
    }, {
        verb: 'GET',
        path: '/user-competency/load/:version',
        description: 'Loads the neural network with version version',
        returns: 'text/plain',
        implemented: false
    }];
    $scope.endpoints = doc;
    $scope.classes = function (verb) {
        switch (verb) {
            case 'GET':
                return 'label label-primary';
            case 'POST':
                return 'label label-success';
            case 'PUT':
                return 'label label-warning';
            case 'DELETE':
                return 'label label-danger';
            default:
                return 'label label-default';
        }
    };
    $scope.payload = function (verb) {
        switch (verb) {
            case 'POST':
            case 'PUT':
                return true;
            default:
                return false;
        }
    };
}]);