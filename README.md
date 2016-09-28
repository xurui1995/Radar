# Radar
雷达图
###效果图  
![](http://p1.bqimg.com/4851/de305a54cc7cbef3.png)  

##提供方法  
```
 //设置标题
    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    //设置数值
    public void setData(double[] data) {
        this.data = data;
    }


    public float getMaxValue() {
        return maxValue;
    }

    //设置最大数值
    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }
    //设置标题颜色
    public void setTextPaintColor(int color){
        textPaint.setColor(color);
    }

    //设置覆盖局域颜色
    public void setValuePaintColor(int color){
        valuePaint.setColor(color);

    }
    //设置雷达图颜色
    public void setMainPaintColor(int color){
        radarPaint.setColor(color);
    }
```  
##使用示例
```
public class MainActivity extends AppCompatActivity {
    private MyRadar mRadar;
    double[] data={100,100,100,100,50,100,20};
    String[] titles={"发球","经验","防守","技巧","速度","力量"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRadar= (MyRadar) findViewById(R.id.radar);

        mRadar.setData(data);
        mRadar.setTitles(titles);

    }
}
```
###其他 
* 默认wrapcontent=300dp  
* 默认MaxValue=100
* 请不要设置过长标题名

    
