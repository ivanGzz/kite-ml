'use strict';
angular.module('competency', []).controller('competencyController', ['$scope', '$http', function ($scope, $http) {
    $scope.competency = {};
    $scope.competency.user_id = '';
    $scope.competency.project_id = '';
    $scope.competency.competencies = '';
    $scope.competency.score = '';
    $scope.send = function (competency) {
        console.log(competency);
        $http.post('/user-competency', {
            'user_id': competency.user_id,
            'project_id': competency.project_id,
            'competencies': competency.split(','),
            'score': competency.score
        }).then(function (res) {
            $scope.competency.user_id = '';
            $scope.competency.project_id = '';
            $scope.competency.competencies = '';
            $scope.competency.score = '';
        }, function (err) {
            console.log(err);
        });
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