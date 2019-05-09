# 笔记内容的搜索功能

## 一、效果截图

（对笔记标题进行模糊插叙）

<img src="screenshots/f2_01.jpg" />

<img src="screenshots/f2_02.jpg" />



## 二、实现思路

在原始项目中，NotesList类继承自ListActivity。而ListActivity有一个默认的布局，该布局仅含一个占满全屏的ListView组件。因此，首先需要改写这个默认布局，以便添加“搜索框组件”。改写的方式可参考[官方文档](https://developer.android.com/reference/android/app/ListActivity)，这里不做赘述。



添加完“搜索框组件”后，对该组件添加相应的事件监听，并实现相应的处理方法，即可实现笔记内容的“实时”搜索功能：

```java
/**
 * 这是一个自定义的方法，用于封装
 * 初始化搜索组件的相关代码。
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
private void initializeSearchView(final SimpleCursorAdapter adapter) {
    SearchView searchView = (SearchView) findViewById(R.id.search_notes);
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            Cursor newCursor;
            // Copy from smartflowers↓
            if (newText != null && !newText.trim().isEmpty()) {
                String selection = NotePad.Notes.COLUMN_NAME_TITLE + " GLOB '*" + newText + "*'";
                newCursor = getContentResolver().query(
                        getIntent().getData(),
                        PROJECTION,
                        selection,
                        null,
                        NotePad.Notes.DEFAULT_SORT_ORDER
                );
            } else {
                newCursor = getContentResolver().query(
                        getIntent().getData(),
                        PROJECTION,
                        null,
                        null,
                        NotePad.Notes.DEFAULT_SORT_ORDER
                );
            }
            adapter.swapCursor(newCursor); // 视图将同步更新！
            return true;
        }
    });
}
```

一个极为重要的一点是，视图总是随着适配器中cursor的变化而同步改变。

