'use strict';
angular.module('sentence', []).controller('sentenceController', ['$scope', '$http', function ($scope, $http) {
    $scope.sentence = {};
    $scope.sentence.content = '';
    $scope.sentence.lang = 'en';
    $scope.sentence.sentiment = '0';
    $scope.sentence.question = false;
    $scope.sentence.common_ground = false;
    $scope.send = function (sentence) {
        console.log(sentence);
        $http.post('/sentence', sentence).then(function (res) {
            $scope.sentence.content = '';
            $scope.sentence.lang = 'en';
            $scope.sentence.sentiment = '0';
            $scope.sentence.question = false;
            $scope.sentence.common_ground = false;
        }, function (err) {
            console.log(err);
        });
    };
    $scope.exists = false;
    $scope.reload = function () {
        $http.get('/sentence/random').then(function (res) {
            $scope.review = res.data;
            $scope.exists = true;
        }, function (err) {
            console.log(err);
        });
    }
    $scope.update = function (review) {
        console.log(review);
        $http.put('/sentence', review).then(function (res) {
            $scope.reload();
        }, function (err) {
            console.log(err);
        });
    };
    $scope.reload();
}]);