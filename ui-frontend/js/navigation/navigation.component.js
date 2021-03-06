angular.
module('navigation').
component('navigation', {
    template: require('./navigation.template.html'),
    controller: ['$rootScope', '$location', '$http',
        function NavigationController($rootScope, $location, $http) {
            this.logout = function () {
                $http.post('api/logout', {}).finally(function () {
                    $rootScope.authenticated = false;
                    $location.path("/login");
                })
            };
        }
    ]
});