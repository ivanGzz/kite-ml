'use strict';
angular.module('sentence', []).controller('sentenceController', ['$scope', '$http', function ($scope, $http) {
    $scope.sentence = {};
    $scope.sentence.content = '';
    $scope.sentence.question = false;
    $scope.sentence.commonGround = false;
    $scope.send = function (sentence) {
        console.log(sentence);
        $http.post('/sentence', sentence).then(function (res) {
            $scope.sentence.content = '';
            $scope.sentence.question = false;
            $scope.sentence.commonGround = false;
        }, function (err) {
            console.log(err);
        });
    };
}]);