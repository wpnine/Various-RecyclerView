# Various-RecyclerView
使RecyclerView 容易去扩展不同样式的item


当RecyclerView需要实现不同样式的item，一般是实现Adapter中的getItemViewType(),接着onCreateViewHolder中创建不同的ViewHolder。
但是当样式相对比较多或者界面的交互相对复杂时(如下图)，可能存在以下几个问题：

  	1.Adapter代码会越来越难臃肿，变得不好维护
  	2.当移除或新增item时，可能影响到其它item
  	3.同一业务中，可能会有不同的页面，存在着一样的item,这时候代码也不要复用
  	4.局部刷新等的计算相对复杂
  	...
    
![image](https://github.com/wpnine/Various-RecyclerView/blob/master/example/assets/ezgif-5-efd0e04e57.gif)



而Various-RecyclerView 是针对列表似的复杂界面而实现的，使不同的item种类进行独立处理，解决整个页面代码臃肿的问题。


# Usage

实现item

    public class LabelItem extends RecyclerItem<List<String>,LabelItem.ViewHolder> {

          @Override
          public LabelItem.LabelViewHolder createChildViewHolder(ViewGroup parent, int viewType) {
              //create viewHolder
              return viewHolder
          }
          
          @Override
          public void onBindViewHolder(LabelItem.LabelViewHolder viewHolder, int position, int viewType) {
          }
          
          @Override
          public int getItemCount(List<String> data) {
                return data.size();
          }

          public static class LabelViewHolder extends RecyclerView.ViewHolder{
                  ......
          }
    }
        
