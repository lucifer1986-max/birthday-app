# 生日提醒应用

一个简约的Android生日提醒应用，支持阴历和阳历，本地存储，提前三天提醒。

## 功能特点

- ✅ 记录亲戚朋友的生日信息
- ✅ 支持阳历和农历两种历法
- ✅ 提前3天开始提醒
- ✅ 本地数据库存储，无需网络
- ✅ 简约清新的界面设计
- ✅ 系统重启后自动恢复提醒

## 构建说明

### 环境要求
- Android Studio 或 Android SDK
- Java 8+
- Gradle

### 构建步骤

1. **使用Android Studio**
   - 打开Android Studio
   - 选择 "Open an existing project"
   - 选择项目根目录
   - 等待Gradle同步完成
   - 点击 "Build" -> "Build Bundle(s) / APK(s)" -> "Build APK(s)"

2. **使用命令行**
   ```bash
   # Windows
   gradlew assembleDebug
   
   # Linux/Mac
   ./gradlew assembleDebug
   ```

3. **使用批处理脚本（Windows）**
   ```bash
   build_apk.bat
   ```

### APK位置
构建完成后，APK文件位于：
```
app/build/outputs/apk/debug/app-debug.apk
```

## 安装说明

1. 将生成的APK文件传输到Android设备
2. 在设备上启用"未知来源"应用安装权限
3. 点击APK文件进行安装
4. 安装完成后，授予应用通知权限

## 使用说明

1. **添加生日**
   - 点击右下角的"+"按钮
   - 输入姓名
   - 选择生日日期
   - 选择历法类型（阳历/农历）
   - 点击保存

2. **查看生日列表**
   - 主界面显示所有已添加的生日
   - 显示姓名、日期、年龄和历法类型

3. **生日提醒**
   - 应用会在生日前3天、2天、1天分别发送通知提醒
   - 提醒时间为每天上午9点

## 技术架构

- **开发语言**: Java
- **最低Android版本**: Android 7.0 (API 24)
- **目标Android版本**: Android 14 (API 34)
- **数据库**: Room (SQLite)
- **UI框架**: Material Design
- **通知**: AlarmManager + BroadcastReceiver

## 项目结构

```
app/
├── src/main/
│   ├── java/com/birthdayreminder/
│   │   ├── MainActivity.java          # 主界面
│   │   ├── AddBirthdayActivity.java   # 添加生日界面
│   │   ├── Birthday.java              # 生日数据模型
│   │   ├── BirthdayDao.java           # 数据访问对象
│   │   ├── BirthdayDatabase.java      # 数据库
│   │   ├── BirthdayAdapter.java       # 列表适配器
│   │   ├── NotificationHelper.java    # 通知助手
│   │   └── BirthdayReceiver.java      # 广播接收器
│   ├── res/
│   │   ├── layout/                    # 布局文件
│   │   ├── values/                    # 资源文件
│   │   └── drawable/                  # 图标资源
│   └── AndroidManifest.xml           # 应用清单
└── build.gradle                      # 构建配置
```

## 许可证

本项目采用MIT许可证，详见LICENSE文件。