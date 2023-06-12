<template>
	<view>
		<!-- 设置状态栏 -->
		<view class="status_bar">
		</view>
		<!-- 自定义导航栏 -->
		<uni-nav-bar backgroundColor="#007fff" color="#ffffff">
			<view slot="default" class="topBarText">
				<!-- 多语言获取 项目名称-->
				{{$t('index.appName')}}
			</view>
			<!-- 设置多语言图标-->
			<view slot="right">
				<cover-image src="/static/images/earth.png"></cover-image>
			</view>
		</uni-nav-bar>
		<view class="picture">
			<swiper class="picture-box">
				<swiper-item v-for="(item, index) in pictureList" :key="index">
					<view class="picture-item">
						<image class="picture-item-style" :src="item" mode="aspectFill"></image>
					</view>
				</swiper-item>
			</swiper>
		</view>
		<view class="indexTitle">
			<view >{{$t('index.appName')}}</view>
			<view class="indexDescription"> Durante el evento, si invitas a nuevos usuarios a unirse al grupo, serás
				recompensado con 2 USD
				Durante el evento, invita a 1-3 personas a recargar y serás recompensado con 5 USD cada uno
				Durante el evento, invita a 4 - 6 personas a recargar y serás recompensado con 10 USD cada uno
				Durante el evento, invita a 7-10 personas a recargar y serás recompensado con 15 USD cada uno
				Método de reclamo: comuníquese con su servidor de atención al cliente exclusivo después de la
				verificación
				para reclamar las recompensas </view>
		</view>

		<view class="welcomeArea">
			<view class="item" @click="toAboutUs()">
				<cover-image src="/static/images/index-about.png"></cover-image>
				<text>{{$t('index.index.about')}}</text>
			</view>
			<view class="item" @click="toTutorial()">
				<cover-image src="/static/images/index-tutorial.png"></cover-image>
				<!-- <cover-image src="/static/images/index-tutorial.png"></cover-image> -->
				<text>{{$t('index.index.Tutorial')}}</text>
			</view>
			<view class="item" @click="toEvents()">
				<cover-image src="/static/images/index-events.png">
				</cover-image>
				<text>{{$t('index.index.Events')}}</text>
			</view>
			<view class="item" @click="toVIPCenter()">
				<cover-image src="/static/images/index-vip.png"></cover-image>
				<text>{{$t('index.index.VIP Center')}}</text>
			</view>
		</view>
		<view class="customerArea" @click="toIndex()" >
			<view class="customer">
				<view class="itemList">
					{{$t('index.index.customer')}}
				</view>
				<view class="">
					<image src="/static/images/customer.png"></image>
				</view>
			</view>

		</view>
		<view class="customerArea" @click="toLink()">
			<view class="customer">
				<view class="itemList">
					{{$t('index.index.conmunity')}}
				</view>
				<view class="">
					<image src="/static/images/conmunity.png"></image>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				systemLocale: '',
				applicationLocale: '',
				options: {
					effect: 'flip',
					autoplay: true,
					direction: 'horizontal',
					loop: true
				},
				pictureList: [
					'/static/images/lunbotu/1.png',
					'/static/images/lunbotu/2.png',
					'/static/images/lunbotu/3.jpg'

				],
				current: 0,
			}
		},

		computed: {
			locales() {
				return [{
						text: this.$t('locale.en'),
						code: 'en'
					},
					{
						text: this.$t('locale.lt'),
						code: 'lt'
					},
					{
						text: this.$t('locale.ru'),
						code: 'ru'
					}
				]
			}
		},
		onLoad() {
			let systemInfo = uni.getSystemInfoSync();
			this.systemLocale = systemInfo.language;
			uni.setLocale('en');
			this.applicationLocale = uni.getLocale();
			this.isAndroid = systemInfo.platform.toLowerCase() === 'android';
			uni.onLocaleChange((e) => {
				this.applicationLocale = e.locale;
			});
			this.iTools.request('noAuth/utils/setLang', {
				lang: 'EN'
			}, 'GET', function(data) {
				console.log(data);
			}, true);
		},
		methods: {
			toAboutUs() {
				uni.navigateTo({
					url: "/pages/index/aboutus"
				});
			},
			toEvents() {
				uni.navigateTo({
					url: "/pages/index/events"
				});
			},
			toVIPCenter() {
				uni.navigateTo({
					url: "/pages/index/VIPcenter"
				});
			},
			toTutorial() {
				uni.navigateTo({
					url: "/pages/index/Tutorial"
				});
			},

			onLocaleChange(e) {
				if (this.isAndroid) {
					uni.showModal({
						content: this.$t('index.language-change-confirm'),
						success: (res) => {
							if (res.confirm) {
								uni.setLocale(e.code);
							}
						}
					});
				} else {
					uni.setLocale(e.code);
					this.$i18n.locale = e.code;
				}
			},
			toLink(){
				uni.navigateTo({
					url:'/pages/index/link'
				})
			},
			toIndex(){
				uni.switchTab({
					url:'/pages/index/index'
				})
			}

		}

	}
</script>

<style>
	.topBarText {
		display: flex;
		justify-content: center;
		align-self: center;
		width: 100%;
		font-size: 1.2rem;
		font-weight: bold;
	}

	.banner {
		width: 98%;
		overflow: hidden;
		margin: 5px auto;
		border-radius: 5px;
		border: 1px solid #9D9D9D;
		box-sizing: border-box;
		line-height: 0;
		padding: 4px;
		background-color: white;
	}

	.bannerImage {
		height: 250px;
		width: 100%;
	}

	.welcomeArea {
		display: flex;
		box-sizing: border-box;
		padding: 7px;
		justify-content: space-between;
		flex-wrap: wrap;
	}

	.indexTitle {
		font-size: 20px;
		color: #333333;
		font-weight: 400;
		text-align: center;
		margin-top: 15px;
	}

	.indexDescription {
		text-align: center;
		color: #444444;
		font-size: 14px;
		padding: 10px 10px 0px 10px;

	}

	.welcomeArea>.item {
		width: 49%;
		height: 80px;
		background-color: white;
		margin-top: 10px;
		border-radius: 10px;
		display: flex;
		box-sizing: border-box;
		padding: 0px 10px;
		align-items: center;
	}

	.welcomeArea>.item>cover-image {
		width: 50px;
		height: 50px;
	}

	.welcomeArea>.item>text {
		font-size: 16px;
		font-weight: bold;
		color: #777777;
		text-indent: 1rem;
	}

	.customerArea {
		background-color: white;
		margin: 10px;
		border-radius: 7px;
	}

	.customerArea>.customer {
		margin-bottom: 10px;
		padding: 0px 15px;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.customerArea>.customer>img {
		padding: 5px;
	}

	.itemList {
		align-items: center;
		color: #777777;
		font-weight: bold;
		font-size: 16px;
		padding-top: ;
		vertical-align: middle;
	}

	.picture-box {
		width: 95%;
		height: 250px;
		margin: 10px auto;
		border-radius: 10px;
	}

	.picture-item-style {
		width: 100%;
		height: 250px;
		border-radius: 10px;
	}
</style>
