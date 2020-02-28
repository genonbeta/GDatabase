package com.genonbeta.android.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by: veli
 * Date: 12/1/16 2:41 PM
 */

public class SQLQuery
{
	public static void createTable(SQLiteDatabase db, SQLValues.Table table)
	{
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("CREATE TABLE ");

		if (table.mayExist())
			stringBuilder.append("IF NOT EXISTS ");

		stringBuilder.append("`");
		stringBuilder.append(table.getName());
		stringBuilder.append("` (");

		int count = 0;

		for (SQLValues.Column columnString : table.getColumns().values())
		{
			if (count > 0)
				stringBuilder.append(", ");

			stringBuilder.append(columnString.toString());

			count++;
		}

		stringBuilder.append(")");

		db.execSQL(stringBuilder.toString());
	}

	public static void createTables(SQLiteDatabase db, SQLValues values)
	{
		for (SQLValues.Table table : values.getTables().values())
			createTable(db, table);
	}

	public static class Select
	{
		private ContentValues mItems = new ContentValues();

		public String tag;
		public String tableName;
		public String[] columns;
		public String where;
		public String[] whereArgs;
		public String groupBy;
		public String having;
		public String orderBy;
		public String limit;
		public LoadListener loadListener;

		public Select(String tableName, String... columns)
		{
			this.tableName = tableName;
			this.columns = columns;
		}

		public ContentValues getItems()
		{
			return mItems;
		}

		public Select setHaving(String having)
		{
			this.having = having;
			return this;
		}

		public Select setOrderBy(String orderBy)
		{
			this.orderBy = orderBy;
			return this;
		}

		public Select setGroupBy(String groupBy)
		{
			this.groupBy = groupBy;
			return this;
		}

		public Select setLimit(int limit)
		{
			return setLimit(String.valueOf(limit));
		}

		public Select setLimit(String limit)
		{
			this.limit = String.valueOf(limit);
			return this;
		}

		public Select setLoadListener(LoadListener listener)
		{
			this.loadListener = listener;
			return this;
		}

		public Select setTag(String tag)
		{
			this.tag = tag;
			return this;
		}

		public Select setWhere(String where, String... args)
		{
			this.where = where;
			this.whereArgs = args;

			return this;
		}

		public static interface LoadListener
		{
			public void onOpen(KuickDb db, Cursor cursor);
			public void onLoad(KuickDb db, Cursor cursor, ContentValues item);
		}
	}
}