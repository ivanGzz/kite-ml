'use strict';
angular.module('documentation', []).controller('documentationController', ['$scope', function ($scope) {
    var doc = [{
        verb: 'GET',
        path: '/',
        description: 'Shows this documentation',
        returns: 'text/html'
    }, {
        verb: 'GET',
        path: '/sentence',
        description: 'Shows the front end for sentences',
        returns: 'text/html'
    }, {
        verb: 'POST',
        path: '/sentence',
        description: 'Creates a new sentences in the database',
        returns: 'text/plain',
        payload: "{\n\tlang: 'en',\n\tcontent: 'This is a new sentence',\n\tsentiment: '0',\n\tquestion: false,\n\tcommon_ground: false\n}"
    }, {
        verb: 'PUT',
        path: '/sentence',
        description: 'Updates an existing sentence in the database',
        returns: 'text/plain',
        payload: "{\n\tid: 1,\n\tsentiment: '0',\n\tquestion: false,\n\tcommon_ground: false\n}"
    }, {
        verb: 'GET',
        path: '/sentence/random',
        description: 'Returns a random sentence that exists in the database',
        returns: 'application/json',
        json: "{\n\tid: 1,\n\tlang: 'en',\n\tcontent: 'This is a new sentence',\n\tsentiment: '0',\n\tquestion: false,\n\tcommon_ground: false\n}"
    }, {
        verb: 'POST',
        path: '/sentence/common-ground',
        description: 'Rates a sentence',
        returns: 'application/json',
        payload: "{\n\tlanguage: 'en',\n\tsentence: 'Why not use the documentation?'\n}",
        json: "{\n\tcommon_ground: true,\n\tsentiment: 0\n}"
    }, {
        verb: 'POST',
        path: '/user-competency',
        description: 'Creates a new user competency in the database and return its rate',
        returns: 'text/plain',
        payload: "{\n\tproject_id: 1,\n\tuser_id: 1,\n\tcompetencies: [0, 1, 2, 3]\n}"
    }, {
        verb: 'PUT',
        path: '/user-competency',
        description: 'Updates an existing user competency in the database and returns its new rate',
        returns: 'text/plain',
        payload: "{\n\tproject_id: 1,\n\tuser_id: 1,\n\tcompetencies: [3, 2, 1, 0]\n}"
    }, {
        verb: 'GET',
        path: '/user-competency/train',
        description: 'Trains the neural network with the user competencies in the database',
        returns: 'text/plain'
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