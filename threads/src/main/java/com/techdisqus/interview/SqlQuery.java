package com.techdisqus.interview;

import java.util.ArrayList;
import java.util.List;

public class SqlQuery {

    private List<String> columnList;
    private String table;
    private String where;
    private List<Condition> conditions;

    public SqlQuery(List<String> columnList, String table, String where, List<Condition> conditions) {
        this.columnList = columnList;
        this.table = table;
        this.where = where;
        this.conditions = conditions;
    }

    public static class Condition {
        private  String column;
        private String value;

        public Condition(String column, String value) {
            this.column = column;
            this.value = value;
        }
    }


    public static class TableSqlQueryBuilder extends  SqlQueryBuilder{
        public SqlQueryBuilder setWhere(String where) {
            this.where = where;
            return this;
        }

    }

    public static class SqlQueryBuilder {

        private List<String> columnList;
        private String table;
        protected String where;
        private List<Condition> conditions;

        public SqlQueryBuilder(){
            columnList = new ArrayList<>();
            conditions = new ArrayList<>();
        }




       public SqlQueryBuilder setTable(String table) {
            this.table = table;
            return this;
        }
        /*

        public SqlQueryBuilder setWhere(String where) {
            this.where = where;
            return this;
        }

        public SqlQueryBuilder addSelectColumn(String column){
            columnList.add(column);
            return  this;

        }

        public SqlQueryBuilder addWhereCondition(String column,String value){
            conditions.add(new Condition(column,value));
            return  this;
        }*/


        public SqlQuery build(){
            return new SqlQuery(columnList,table,where,conditions);
        }

    }

    public static void main(String[] args) {
        SqlQueryBuilder sqlQueryBuilder = new SqlQueryBuilder();



    }

}
