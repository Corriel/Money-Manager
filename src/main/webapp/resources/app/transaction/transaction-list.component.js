angular.
module('transaction').
component('transactionList', {
    templateUrl: 'app/transaction/transaction-list.template.html',
    controller: ['Transaction', '$routeParams', "$scope",
        function TransactionListController(Transaction, $routeParams, $scope) {
            var self = this;
            var initTransactions = function() {
                self.transactions = Transaction.query({id: $routeParams.id});
            };

            initTransactions();

            $scope.$on('transactionInsert', function() {
                initTransactions();
            });
        }
    ]
});