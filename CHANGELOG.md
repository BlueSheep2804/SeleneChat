# Changelog

## [0.3.0](https://github.com/BlueSheep2804/SeleneChat/compare/v0.2.0...v0.3.0) (2025-01-08)


### Features

* 🎸 /channel joinコマンドでグローバルチャンネルに切り替え可能に ([15f8279](https://github.com/BlueSheep2804/SeleneChat/commit/15f8279c41b11c28cc97b29ab46562756ca0a8c3))
* 🎸 /channel leaveコマンドの空引数の挙動を変更 ([5ec2a15](https://github.com/BlueSheep2804/SeleneChat/commit/5ec2a15762f6566c64482b8f3006adc78c5216d0))
* 🎸 allChannelsをmapに変更 ([e2a27dc](https://github.com/BlueSheep2804/SeleneChat/commit/e2a27dc2fd43808e20d3fee687e7c0eeec41fa06))
* 🎸 channel listコマンドを見やすいものに変更 ([88e2ab5](https://github.com/BlueSheep2804/SeleneChat/commit/88e2ab5e5789575c0a187b773af4b92f1203a2dc))
* 🎸 channelManagerをreloadコマンドの再読み込み対象に追加 ([bf652fd](https://github.com/BlueSheep2804/SeleneChat/commit/bf652fd91b497bb018e5e66d11dc606cdb4d4814))
* 🎸 channelコマンドのサジェストを改善 ([bedd27e](https://github.com/BlueSheep2804/SeleneChat/commit/bedd27e6fa3d6148e0bbb0bde6e70202c346e551))
* 🎸 channelサブコマンドの権限を追加 ([a0f7dd7](https://github.com/BlueSheep2804/SeleneChat/commit/a0f7dd728d7719981709f9f00c6b8c807ce892ab))
* 🎸 ResourceDataにバージョンを追加 ([6ac76ab](https://github.com/BlueSheep2804/SeleneChat/commit/6ac76ab02f17d2e5d0529f63d7c6c19734fe10de))
* 🎸 ResourceManagerに保存機能を追加 ([1e4d51e](https://github.com/BlueSheep2804/SeleneChat/commit/1e4d51ea720753b14dfd466b5adedfb2422f8325))
* 🎸 グローバルマーカーを追加 ([2be6ffa](https://github.com/BlueSheep2804/SeleneChat/commit/2be6ffae716f41f96a41714e58d68977b5be71d4))
* 🎸 コマンドの結果に接頭辞を追加 ([6fbe2e0](https://github.com/BlueSheep2804/SeleneChat/commit/6fbe2e00d39cb8fa20b571faa0d5816f3b0f2b77))
* 🎸 コンソールプレイヤーのクラスを改善 ([e1cddf9](https://github.com/BlueSheep2804/SeleneChat/commit/e1cddf9b0e7de3e5b440c4f654703a53e795a420))
* 🎸 コンフィグファイルのアップデート時に自動で更新するように変更 ([ecf4175](https://github.com/BlueSheep2804/SeleneChat/commit/ecf41753eba028f460ab8ca6ecae438b13132b1c))
* 🎸 チャンネルごとに変換モードを指定できるように ([0b7c689](https://github.com/BlueSheep2804/SeleneChat/commit/0b7c689088247e3f69b2e1893da325ada890c204))
* 🎸 チャンネルチャットを実装 ([2279c5f](https://github.com/BlueSheep2804/SeleneChat/commit/2279c5fc8ef3b7107cbc71914e5397ff0f730b1a))
* 🎸 チャンネルにプレイヤーが入退室する仕組みを追加 ([6648461](https://github.com/BlueSheep2804/SeleneChat/commit/66484613d90062f24355f49ede75dd7bdd9d111a))
* 🎸 チャンネルの情報を表示するコマンドを追加 ([478975c](https://github.com/BlueSheep2804/SeleneChat/commit/478975c2f51238a6541b6f8edc79c54807e590cd))
* 🎸 チャンネルの表示名をMiniMessage形式に変更 ([9b9e607](https://github.com/BlueSheep2804/SeleneChat/commit/9b9e607585f3e4ca993121dde963506ec9654ae3))
* 🎸 チャンネルの設定をコマンドから変更できるように ([2816ae7](https://github.com/BlueSheep2804/SeleneChat/commit/2816ae71ec2c3ca43448d7b79eb8bdc23edc6cf3))
* 🎸 チャンネルファイルの自動更新機能を追加 ([e6743ad](https://github.com/BlueSheep2804/SeleneChat/commit/e6743ade394583a818acfb41c9083d75e6f98d37))
* 🎸 チャンネルを削除するコマンドを追加 ([9aeada2](https://github.com/BlueSheep2804/SeleneChat/commit/9aeada25804bcd064e30b0ef5b32ba44e831de07))
* 🎸 チャンネルを操作するためのコマンドを追加 ([f10b0f7](https://github.com/BlueSheep2804/SeleneChat/commit/f10b0f7716335f3231dfc130fb0dbdb58293c00a))
* 🎸 チャンネルを管理するクラスを追加 ([059677f](https://github.com/BlueSheep2804/SeleneChat/commit/059677f94c2665e3950df233fe703a0c322c9d07))
* 🎸 チャンネルを隠す設定を追加 ([22b8d93](https://github.com/BlueSheep2804/SeleneChat/commit/22b8d9332a8cae59f58ebf12ea1062d776284868))
* 🎸 チャンネル作成時の重複をエラーとして返すように変更 ([d688c51](https://github.com/BlueSheep2804/SeleneChat/commit/d688c51c9eb51146bac2cadc68553c9776227f6f))
* 🎸 チャンネル機能を無効化できる設定を追加 ([f1ad739](https://github.com/BlueSheep2804/SeleneChat/commit/f1ad7396c83f54ddf422bef143fca170106d76d3))
* 🎸 チャンネル用のチャットフォーマット設定を追加 ([d68e0d5](https://github.com/BlueSheep2804/SeleneChat/commit/d68e0d552e283a957cd3cf9c1062dcbfa228ea7c))
* 🎸 モデレーター機能を実装 ([1594d53](https://github.com/BlueSheep2804/SeleneChat/commit/1594d5371662e47ad2379c2cadbb8b6e016e0c3e))
* 🎸 発言先の変更機能を追加 ([246580b](https://github.com/BlueSheep2804/SeleneChat/commit/246580bdbef4ee9adab4b426c44a04f2bc038379))


### Bug Fixes

* 🐛 ChannelDataのプロパティを変更できない問題を修正 ([e2368c8](https://github.com/BlueSheep2804/SeleneChat/commit/e2368c80d4e1d54e7ac032ca22d3ca0ef0228621))
* 🐛 channelのサブコマンドが空か存在しない場合に内部的にはエラーになっていなかった問題を修正 ([5342d76](https://github.com/BlueSheep2804/SeleneChat/commit/5342d761a21e8b7046f7210311fcb4088116fea5))
* 🐛 コンソールからchannelコマンドを実行した際の様々な不具合を修正 ([3b7befc](https://github.com/BlueSheep2804/SeleneChat/commit/3b7befc2f8e25dd4bf39e8a1597039515cd961fb))
* 🐛 チャンネルから退出した際に発言先が切り替わらなかった問題を修正 ([e6405ac](https://github.com/BlueSheep2804/SeleneChat/commit/e6405acee191afba2f613c7d1b36829ecff6b428))
* 🐛 チャンネルの変換設定のサジェストがうまく機能していなかった問題を修正 ([e49719d](https://github.com/BlueSheep2804/SeleneChat/commit/e49719d2402e16d02f373fd0f99c4a2af324ecf9))
* 🐛 チャンネルの編集コマンドのサジェストがうまく機能していなかった問題を修正 ([7d28e9c](https://github.com/BlueSheep2804/SeleneChat/commit/7d28e9cdd3206e2a9690e618d74ce32b0c1cd1c4))
* 🐛 チャンネルの設定を表示する際にエラー扱いになっていた不具合を修正 ([da1046f](https://github.com/BlueSheep2804/SeleneChat/commit/da1046faf64ccd31496bcf60464bb26c999ec20c))
* 🐛 チャンネル作成時に小文字に強制するように ([6b0ad11](https://github.com/BlueSheep2804/SeleneChat/commit/6b0ad11e71802d1848956c2290d9eeeeb439e6ca))
* 🐛 プライベートメッセージにSeleneChatの接頭辞が付いていた問題を修正 ([85cd6c0](https://github.com/BlueSheep2804/SeleneChat/commit/85cd6c082d982ad74d33ff3d2d89217d5c903161))

## [0.2.0](https://github.com/BlueSheep2804/SeleneChat/compare/v0.1.1...v0.2.0) (2023-07-21)


### Features

* 🎸 Bukkitに対応 ([7d10843](https://github.com/BlueSheep2804/SeleneChat/commit/7d10843bea08581c151d4fb81b0ab9862a775bd8))
* 🎸 japanize.ymlを再読込できるように ([763bd50](https://github.com/BlueSheep2804/SeleneChat/commit/763bd50f3aef50217affaf0a39cd0b526fa5bad8))
* 🎸 japanizeコマンドをLunaChatの仕様に近い構文に変更 ([90569df](https://github.com/BlueSheep2804/SeleneChat/commit/90569dfc5145ad7fc10b93c465f45d4905959b8e))
* 🎸 japanize変換の状態を保存するファイルの仕様を変更 ([d28c4d7](https://github.com/BlueSheep2804/SeleneChat/commit/d28c4d7ece67371ed4ce23311a2a9aef9cbf8480))
* 🎸 japanize変換の状態を保存するファイルの名前を変更 ([a28e13a](https://github.com/BlueSheep2804/SeleneChat/commit/a28e13ac3b34ee877d178696240df843e3255320))
* 🎸 japanize変換の状態を変更したタイミングでファイルに書き込まれる仕様に変更 ([cf5d68e](https://github.com/BlueSheep2804/SeleneChat/commit/cf5d68eff9fef98e6d62b0700cfb86cb0f7005ea))
* 🎸 PluginMessageの仕様を変更 ([eea1729](https://github.com/BlueSheep2804/SeleneChat/commit/eea17292a70b6a6dcfa899aca1f29e04918134aa))
* 🎸 selenechatのサブコマンドに権限を割り当てた ([e318061](https://github.com/BlueSheep2804/SeleneChat/commit/e3180611ce5f01ffd327a70a7d5c185da1e0313f))
* 🎸 アイコンを追加 ([3213cb7](https://github.com/BlueSheep2804/SeleneChat/commit/3213cb77824af76954b25217e4b55cce6125293e))
* 🎸 オフラインプレイヤーを追加 ([82a3457](https://github.com/BlueSheep2804/SeleneChat/commit/82a34578d5aa8d4cf459d16d5a0f463ef3a92de7))
* 🎸 カラーコードによる装飾に権限を設定できるように ([e7a60c7](https://github.com/BlueSheep2804/SeleneChat/commit/e7a60c731fa537bad734b370cf84e1b5493e00a0))
* 🎸 コンフィグを保存するメソッドの追加 ([d9a1481](https://github.com/BlueSheep2804/SeleneChat/commit/d9a148104815ca7043034c044d24d023ebd696d1))
* 🎸 チャット内でカラーコードを使用できるように ([b14fd9f](https://github.com/BlueSheep2804/SeleneChat/commit/b14fd9fbf8e8ac8a9d570af4a5693ca2a8c080ca))
* 🎸 プレイヤークラスに権限を検証するメソッドを追加 ([28161cd](https://github.com/BlueSheep2804/SeleneChat/commit/28161cd8f9ebec5dcfedcce00081d517acaaadbd))
* 🎸 一時的に日本語化を無効化できるように ([9e42abf](https://github.com/BlueSheep2804/SeleneChat/commit/9e42abf5f2ea3b887b1ea32569712124feddc83b))
* 🎸 文字列リソースを更新 ([8bf2fd1](https://github.com/BlueSheep2804/SeleneChat/commit/8bf2fd15f444c21cc72d7bd6895f3a3a8650faf2))
* 🎸 日本語化のデフォルト設定を追加 ([f1df210](https://github.com/BlueSheep2804/SeleneChat/commit/f1df2109c6f14348483b3a02f7e090ffa4d2dce3))
* 🎸 日本語化の有無をコマンドで切り替えできるように ([069b3d3](https://github.com/BlueSheep2804/SeleneChat/commit/069b3d34e967821be7d5b10268cc9130f184a27b))
* 🎸 権限周りの調整 ([e15f9cc](https://github.com/BlueSheep2804/SeleneChat/commit/e15f9ccbf8e337bed2df1a770ec28b16bd5a9be1))


### Bug Fixes

* 🐛 japanize変換の状態が重複する不具合 ([f3d71e9](https://github.com/BlueSheep2804/SeleneChat/commit/f3d71e945adf96a0deac0099e2e9e1904c6b9af2))
* 🐛 PlainTextSerializerがインポートできない ([e48247f](https://github.com/BlueSheep2804/SeleneChat/commit/e48247f01ba49c10d44e5172c3666567452023c6))
* 🐛 コマンド引数のサジェストがうまく機能しない不具合 ([00add08](https://github.com/BlueSheep2804/SeleneChat/commit/00add08409401d42078a2d7f193152d874a11ae3))

## [0.1.1](https://github.com/BlueSheep2804/SeleneChat/compare/v0.1.0...v0.1.1) (2023-07-11)


### Bug Fixes

* 🐛 サーバー名のホバーテキストがmessage.ymlで変更できない ([8ab298d](https://github.com/BlueSheep2804/SeleneChat/commit/8ab298d38035e4d253b2d9b7f81678ccb561bc57))

## 0.1.0 (2023-07-08)


### Features

* 🎸 Bungeecordサポートの追加 ([5da8595](https://github.com/BlueSheep2804/SeleneChat/commit/5da85958ac5a069bfd8bc538f7729b1898678d54))
* 🎸 configを参照方式に ([681127d](https://github.com/BlueSheep2804/SeleneChat/commit/681127d9c7e266d0a69e68e61be17320c575b791))
* 🎸 first commit ([0a5a247](https://github.com/BlueSheep2804/SeleneChat/commit/0a5a247283dff995794a17006487623e11c8c530))
* 🎸 minimessageを用いてフォーマットを指定できるように ([2d58cbf](https://github.com/BlueSheep2804/SeleneChat/commit/2d58cbfb8f276de385eeffa944118dd67fc24b05))
* 🎸 pluginMessage周りの調整 ([a356fd3](https://github.com/BlueSheep2804/SeleneChat/commit/a356fd3830613e02ba8461e8d3ceb875ec8f6546))
* 🎸 shouldShowServerNameを削除 ([14ebf64](https://github.com/BlueSheep2804/SeleneChat/commit/14ebf6487da0a110e97b04c16a68d537c8361a5b))
* 🎸 spigotサポートを追加 ([ac4efcb](https://github.com/BlueSheep2804/SeleneChat/commit/ac4efcba37a3c893f4c0d92e7527c4e998d25505))
* 🎸 spigot環境で設定ファイルで指定したフォーマットでチャットを返す設定の追加 ([6445f6e](https://github.com/BlueSheep2804/SeleneChat/commit/6445f6eed17cdf6444885f619ac8ac4e333b2982))
* 🎸 グローバルチャットにサーバー名を追加 ([5919c86](https://github.com/BlueSheep2804/SeleneChat/commit/5919c86c8b7197a6e4cce8a3a8bd14a614a8d0ca))
* 🎸 コマンドの仕様をまとめた ([2aea0d7](https://github.com/BlueSheep2804/SeleneChat/commit/2aea0d7d7247c17aa0c79b2f821424476998b0e8))
* 🎸 コンフィグ、リソースファイルの文字制限を少し長くした ([0079af0](https://github.com/BlueSheep2804/SeleneChat/commit/0079af0441ff3d192595f15bd89a44e6517cf882))
* 🎸 チャットイベントを拾って整形するメソッドを追加 ([84325e0](https://github.com/BlueSheep2804/SeleneChat/commit/84325e06333d29d21e09cc0e9c3b75219ec40e95))
* 🎸 チャットのフォーマット周りの改善 ([1cff8ec](https://github.com/BlueSheep2804/SeleneChat/commit/1cff8eccbac3e84b21e3e8f91096e20b40438778))
* 🎸 チャットを自動で日本語化するように ([248ebc7](https://github.com/BlueSheep2804/SeleneChat/commit/248ebc7256da70f8988cafb51f7290a887dceb1e))
* 🎸 プライベートメッセージを送れるコマンドを実装 ([7de81c6](https://github.com/BlueSheep2804/SeleneChat/commit/7de81c6fd9285a9b5aa4ce90b6fb1ce6a1f647b2))
* 🎸 プレイヤーの差分を埋めるSeleneChatPlayerを実装 ([1ff5b10](https://github.com/BlueSheep2804/SeleneChat/commit/1ff5b10ad840b622d71305299f26af36d5ec8e5f))
* 🎸 プレイヤー名にホバーしたときの表示をバニラに合わせた ([ad8474a](https://github.com/BlueSheep2804/SeleneChat/commit/ad8474ab3778c2b886343eb51eccf0ded77c7a3f))
* 🎸 プロキシサーバー用のグローバルチャットにPlugin Messageを使用する方式の追加 ([0024a10](https://github.com/BlueSheep2804/SeleneChat/commit/0024a10d6a229c111092efe1c60eaeaab7c22a0a))
* 🎸 一部動作を設定に依存するように ([274c2bf](https://github.com/BlueSheep2804/SeleneChat/commit/274c2bf56c8bd84ca6f0b5df93a261584c7aff8b))
* 🎸 文字列をファイルで管理できるように ([518068d](https://github.com/BlueSheep2804/SeleneChat/commit/518068d8b23cc77de394de86202ac9589867d9ed))
* 🎸 日付と時刻をチャットフォーマットに追加 ([2dbc2b1](https://github.com/BlueSheep2804/SeleneChat/commit/2dbc2b13cb7b05634ca5a901595cfc63c847e4a1))
* 🎸 日本語化が必要かどうか判断し、必要ない場合は処理をスキップするように変更 ([adfa140](https://github.com/BlueSheep2804/SeleneChat/commit/adfa14084519b2b57a213e72939e4d0129ec116d))
* 🎸 設定ファイル用クラスの構造を変更 ([a6af7ed](https://github.com/BlueSheep2804/SeleneChat/commit/a6af7ed432e49a5d7ccdce1c69c2edfa70cdb819))
* 🎸 設定を保存するための用意 ([b85aee7](https://github.com/BlueSheep2804/SeleneChat/commit/b85aee7c689c2bdc149734406550124768beddee))
* 🎸 設定を再読み込みするコマンドを追加 ([4d58621](https://github.com/BlueSheep2804/SeleneChat/commit/4d5862101d9b756e466498e75566b4f73abcf43c))


### Bug Fixes

* 🐛 japanizerの無変換モード時に空文字が返されない問題を修正 ([1de8354](https://github.com/BlueSheep2804/SeleneChat/commit/1de83541f83a57eda43491cf774646c376667ec7))
* 🐛 messageコマンドのメッセージが第二引数以降を無視していた問題を修正 ([b0331f7](https://github.com/BlueSheep2804/SeleneChat/commit/b0331f747daa21f00929085a4681edcb24af848c))
* 🐛 selenechatコマンドで権限が確認されない問題を修正 ([731c50e](https://github.com/BlueSheep2804/SeleneChat/commit/731c50eec82578cb65cd0d3991c2a42de0c54ad4))
* 🐛 spigotでチャットフォーマットが適用されない問題を修正 ([38087f8](https://github.com/BlueSheep2804/SeleneChat/commit/38087f8d1f55c32fdac9c37ca75b6854e66f39f0))
* 🐛 チャットフォーマットが適用されない問題を修正 ([c26dcff](https://github.com/BlueSheep2804/SeleneChat/commit/c26dcff347f3d28c4cb85fc5ca35019dbf66ddbf))
* 🐛 プラグインインスタンス初期化時の実行順を変更 ([f60b9ed](https://github.com/BlueSheep2804/SeleneChat/commit/f60b9ed8798a511e176c7bead5345a73d83d83bb))
* 🐛 プラグインの情報の間違っている個所を修正 ([76c92a4](https://github.com/BlueSheep2804/SeleneChat/commit/76c92a470e42d0c35b58d9bae4d9352c652f941a))
* 🐛 ホバーメッセージの修正 ([3b2a9f3](https://github.com/BlueSheep2804/SeleneChat/commit/3b2a9f3727c8165034ac527bde7bbf9306e00e73))


### Miscellaneous Chores

* 🤖 release 0.1.0 ([8de6d97](https://github.com/BlueSheep2804/SeleneChat/commit/8de6d97bb10be485890fa1c82f075650153e727b))
