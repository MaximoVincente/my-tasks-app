package com.maximo.mytaskmanager.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.maximo.mytaskmanager.models.Task;
import java.lang.Class;
import java.lang.IllegalArgumentException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TaskDao_Impl implements TaskDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Task> __insertionAdapterOfTask;

  private final EntityDeletionOrUpdateAdapter<Task> __deletionAdapterOfTask;

  private final EntityDeletionOrUpdateAdapter<Task> __updateAdapterOfTask;

  public TaskDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTask = new EntityInsertionAdapter<Task>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Task` (`id`,`taskTitle`,`taskDescription`,`state`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Task value) {
        if (value.id == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.id);
        }
        if (value.getTaskTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTaskTitle());
        }
        if (value.getTaskDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTaskDescription());
        }
        if (value.getState() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, __TaskStateEnum_enumToString(value.getState()));
        }
      }
    };
    this.__deletionAdapterOfTask = new EntityDeletionOrUpdateAdapter<Task>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Task` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Task value) {
        if (value.id == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.id);
        }
      }
    };
    this.__updateAdapterOfTask = new EntityDeletionOrUpdateAdapter<Task>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Task` SET `id` = ?,`taskTitle` = ?,`taskDescription` = ?,`state` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Task value) {
        if (value.id == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.id);
        }
        if (value.getTaskTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTaskTitle());
        }
        if (value.getTaskDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTaskDescription());
        }
        if (value.getState() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, __TaskStateEnum_enumToString(value.getState()));
        }
        if (value.id == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, value.id);
        }
      }
    };
  }

  @Override
  public void insertATask(final Task task) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTask.insert(task);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Task task) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfTask.handle(task);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Task task) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfTask.handle(task);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Task> findAll() {
    final String _sql = "SELECT * FROM Task";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTaskTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "taskTitle");
      final int _cursorIndexOfTaskDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "taskDescription");
      final int _cursorIndexOfState = CursorUtil.getColumnIndexOrThrow(_cursor, "state");
      final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Task _item;
        _item = new Task();
        if (_cursor.isNull(_cursorIndexOfId)) {
          _item.id = null;
        } else {
          _item.id = _cursor.getLong(_cursorIndexOfId);
        }
        final String _tmpTaskTitle;
        if (_cursor.isNull(_cursorIndexOfTaskTitle)) {
          _tmpTaskTitle = null;
        } else {
          _tmpTaskTitle = _cursor.getString(_cursorIndexOfTaskTitle);
        }
        _item.setTaskTitle(_tmpTaskTitle);
        final String _tmpTaskDescription;
        if (_cursor.isNull(_cursorIndexOfTaskDescription)) {
          _tmpTaskDescription = null;
        } else {
          _tmpTaskDescription = _cursor.getString(_cursorIndexOfTaskDescription);
        }
        _item.setTaskDescription(_tmpTaskDescription);
        final Task.TaskStateEnum _tmpState;
        _tmpState = __TaskStateEnum_stringToEnum(_cursor.getString(_cursorIndexOfState));
        _item.setState(_tmpState);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Task findById(final long id) {
    final String _sql = "SELECT * FROM Task WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTaskTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "taskTitle");
      final int _cursorIndexOfTaskDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "taskDescription");
      final int _cursorIndexOfState = CursorUtil.getColumnIndexOrThrow(_cursor, "state");
      final Task _result;
      if(_cursor.moveToFirst()) {
        _result = new Task();
        if (_cursor.isNull(_cursorIndexOfId)) {
          _result.id = null;
        } else {
          _result.id = _cursor.getLong(_cursorIndexOfId);
        }
        final String _tmpTaskTitle;
        if (_cursor.isNull(_cursorIndexOfTaskTitle)) {
          _tmpTaskTitle = null;
        } else {
          _tmpTaskTitle = _cursor.getString(_cursorIndexOfTaskTitle);
        }
        _result.setTaskTitle(_tmpTaskTitle);
        final String _tmpTaskDescription;
        if (_cursor.isNull(_cursorIndexOfTaskDescription)) {
          _tmpTaskDescription = null;
        } else {
          _tmpTaskDescription = _cursor.getString(_cursorIndexOfTaskDescription);
        }
        _result.setTaskDescription(_tmpTaskDescription);
        final Task.TaskStateEnum _tmpState;
        _tmpState = __TaskStateEnum_stringToEnum(_cursor.getString(_cursorIndexOfState));
        _result.setState(_tmpState);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Task> findAllByType(final Task.TaskStateEnum state) {
    final String _sql = "SELECT * FROM Task WHERE state = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (state == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, __TaskStateEnum_enumToString(state));
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTaskTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "taskTitle");
      final int _cursorIndexOfTaskDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "taskDescription");
      final int _cursorIndexOfState = CursorUtil.getColumnIndexOrThrow(_cursor, "state");
      final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Task _item;
        _item = new Task();
        if (_cursor.isNull(_cursorIndexOfId)) {
          _item.id = null;
        } else {
          _item.id = _cursor.getLong(_cursorIndexOfId);
        }
        final String _tmpTaskTitle;
        if (_cursor.isNull(_cursorIndexOfTaskTitle)) {
          _tmpTaskTitle = null;
        } else {
          _tmpTaskTitle = _cursor.getString(_cursorIndexOfTaskTitle);
        }
        _item.setTaskTitle(_tmpTaskTitle);
        final String _tmpTaskDescription;
        if (_cursor.isNull(_cursorIndexOfTaskDescription)) {
          _tmpTaskDescription = null;
        } else {
          _tmpTaskDescription = _cursor.getString(_cursorIndexOfTaskDescription);
        }
        _item.setTaskDescription(_tmpTaskDescription);
        final Task.TaskStateEnum _tmpState;
        _tmpState = __TaskStateEnum_stringToEnum(_cursor.getString(_cursorIndexOfState));
        _item.setState(_tmpState);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private String __TaskStateEnum_enumToString(final Task.TaskStateEnum _value) {
    if (_value == null) {
      return null;
    } switch (_value) {
      case NEW: return "NEW";
      case ASSIGNED: return "ASSIGNED";
      case INPROGRESS: return "INPROGRESS";
      case COMPLETE: return "COMPLETE";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private Task.TaskStateEnum __TaskStateEnum_stringToEnum(final String _value) {
    if (_value == null) {
      return null;
    } switch (_value) {
      case "NEW": return Task.TaskStateEnum.NEW;
      case "ASSIGNED": return Task.TaskStateEnum.ASSIGNED;
      case "INPROGRESS": return Task.TaskStateEnum.INPROGRESS;
      case "COMPLETE": return Task.TaskStateEnum.COMPLETE;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}
