# Foodie
一款擁有探索，搜尋，收藏，記錄功能的美食地圖 app<br/>
歡迎各位美食家們一起加入!

<br />
[<img src="https://github.com/steverichey/google-play-badge-svg/blob/master/img/en_get.svg" width="25%" height="25%">](https://play.google.com/store/apps/details?id=com.guanhong.foodie)

# Features

  * [登入頁面]
    * 使用 Fire Authentication 登入
  
  * [主頁面 - 地圖]
    * 按照使用者的上傳紀錄設置每個餐廳圖標
    * 上下滑動地圖可以檢視各個餐廳的圖標
    * 點擊定位按鈕將鏡頭移至目前所在地
    * 點擊任一餐廳圖標可查看該餐廳的預覽資訊
    * 點擊任一餐廳預覽資訊可進入該餐廳的[細節頁面]
  
  * [主頁面 - 搜尋]
    * 使用上方的輸入框輸入餐廳名稱或餐廳地址可以顯示篩選過後有搜尋關鍵字的餐廳列表
    * 搜尋不到餐廳時顯示結果空白
    * 點擊任一餐廳項目可進入該餐廳的[細節頁面]

 * [主頁面 - 收藏]
    * 在餐廳[細節頁面]點擊書籤即可把餐廳加入收藏清單 <br />
    * 加入收藏或取消收藏會跳出訊息告知使用者 <br />
    * 若玩家已收藏該間餐廳，則每次進入該餐廳時書籤會顯示為已選擇書籤 <br />
    * 收藏頁面以清單方式顯示已收藏餐廳 
 
 *  [主頁面 - 推薦]  
    * 每日更新一間五星級餐廳並把資訊顯示在推薦頁面
    * 餐廳文字使用第三方套件讓文字出現閃爍效果(Shimmer Libraries) 
    * 第一次進入推薦頁面時以動畫方式顯示布條及本日精選字樣
  
 *  [主頁面 - 個人檔案]  
    * 顯示登入時輸入的個人資訊 : 姓名和Email
    * 首次註冊時個人頭像為預設 icon, 點擊 icon 可從相簿選擇相片更換個人頭像 
    * 清單方式顯示個人已發布過的文章
 
  * [餐廳頁面]  
    * 顯示餐廳名稱，地址，評分，照片，遊客文章，訪客留言
    * 相片 gallery 無限循環顯示
    * 點擊書籤可將該餐廳加入收藏清單
    * 左右滑動顯示遊客文章，點擊後進入遊客文章頁面
    * 可以在下方留言區對該餐廳留下簡短評論
    
 *  [發文頁面]  
    * 需輸入餐廳名稱，選擇地址，相片，評分，菜單，心得
    * 點擊地圖圖案進入地圖選擇餐廳地址
    * 點擊相片可從相簿選擇餐廳相片上傳，上限十張
 
  
# Screenshot

  <img src="https://lh3.googleusercontent.com/9GxITEIOavpsGqwb47zkbNHN1ZBxj6aC_h-ryLYD2MuoJBc_gsPRTdDIbz-s1TxKcg=w720-h310-rw" width="210"> <img src="https://lh3.googleusercontent.com/6ZY_dCjNDrPdtxJGGDK5Kl8vn7fz41E4CttKGoYfE7TpGnJ1adQzNVzpIf20R8R1yGk=w720-h310-rw" width="210"> <img src="https://lh3.googleusercontent.com/yYI8Kv4B22dP1S2SQicE24MIqJwWF0e8Ie_o0ciI6dWZmYOQ-7RsPY_aGPoH2XUcGU9k=w720-h310-rw" width="210"> <img src="https://lh3.googleusercontent.com/KbNRoQi4HmJcdeZFVqvBKSuyFnWsJB7tkW2Cj2I0jgC0dx6244n1tmiIyMTSL-Leciw=w720-h310-rw" width="210">

# Implemented
  
  * Design Patterns 
    * Objecr-Oreinted-Programming
    * Model-View-Presenter (MVP) 
    * Singleton 
    * Adapter	
    
  * Core Functions 
    * Firebase Login 
    * Google Map
    
  * User Interface
    * TabLayout
    * ViewPager 
    * Fragment 
    * RecyclerView  
    * Animation
    
  * Storage
    * SharedPreferences  
    * Firebase Authentication
    * Firebase Realtime Database
    * Firebase Storage
    
  * Analysis
    * Firebase Crash Analytics 	
    
  * Unit Test
    * JUnit 
    * Mockito	
    * Espresso

# Requirement
* Android Studio 3.0+
* Android SDK 23+

# Version
* 1.0.6 - 2018/11/04

  * 調整部分 UI 顯示

# Contact
david840801@gmail.com 
