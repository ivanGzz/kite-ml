'use strict';
angular.module('competency', [])
.directive('numericarray', [function () {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function (scope, element, attr, ngModel) {
            function fromUser(text) {
                return text.split(',').map(function (e) {
                    return parseInt(e);
                });
            }
            function toUser(array) {
                return array.join(',');
            }
            ngModel.$parsers.push(fromUser);
            ngModel.$formatters.push(toUser);
        }
    }
}])
.controller('competencyController', ['$scope', '$http', function ($scope, $http) {
    $scope.competency = {};
    $scope.competency.user_id = '';
    $scope.competency.project_id = '';
    $scope.competency.competencies = [];
    $scope.competency.score = '';
    $scope.send = function (competency) {
        console.log(competency);
        /*$http.post('/user-competency', competency).then(function (res) {
            $scope.competency.user_id = '';
            $scope.competency.project_id = '';
            $scope.competency.competencies = [];
            $scope.competency.score = '';
        }, function (err) {
            console.log(err);
        });*/
    };
    $scope.exists = false;
    $scope.review = {};
    $scope.reload = function () {
        $http.get('/user-competency/random').then(function (res) {
            $scope.review = res.data;
            $scope.exists = true;
        }, function (err) {
            console.log(err);
        })
    };
    $scope.reload();
}]);