<template>
	<view class="loginBG">
		<view class="status_bar">
		</view>
		<uni-nav-bar backgroundColor="#007fff" color="#ffffff">
			<view slot="left" @click="goHome">
				<uni-icons type="home-filled" size="30" color="#ffffff"></uni-icons>
			</view>
			<view class="topBarText" slot="default">
				{{$t('global.appName')}}
			</view>
			<view slot="right">
				<cover-image src="/static/images/earth.png"></cover-image>
			</view>
		</uni-nav-bar>
		<view class="loginForm">
			<view class="logo">
				<cover-image src="/static/images/loginLogo.png"></cover-image>
			</view>
		</view>
		<view class="loginArea">
			<view class="registerForm">
				<view class="phone">
					<uni-data-picker v-model="currentArea" :ellipsis="false" :clear-icon="false" :localdata="areaCode">
					</uni-data-picker>
					<input type="number" v-model="phone" :placeholder="$t('login.form.phone')" />
				</view>
				<view class="verifyCode">
					<input type="number" v-model="verifyCode" :placeholder="$t('me.register.getVerifyCode')" />
					<button type="primary"  @click="getVerifyCode()">
						{{countDownTime}}
					</button>
				</view>
				<view class="password">
					<input type="password" v-model="password" :placeholder="$t('login.form.password')" />
				</view>
				<button type="primary" @click="register()">{{$t('login.form.signUpNow')}}</button>
			</view>
		</view>

	</view>

</template>

<script>
	export default {
		data() {
			return {
				baseURL: 'http://111.67.207.131.:4869',
				areaCode: [{
					text: '007( RU )',
					value: 7
				}, {
					text: '370( LT )',
					value: 370,
					selected: true
				}, {
					text: '001( US )',
					value: 1
				}],
				currentArea: 370,
				phone: '',
				verifyCode: '',
				password: '',
				accessToken: '',
				countDownTime: this.$t('me.register.getVerifyCode'),
				
			}
		},
		methods: {
			goHome() {
				uni.switchTab({
					url: '/pages/index/index'
				});
			},
			register() {
				let that = this;
				let phone = this.$data.phone.trim();
				let password = this.$data.password.trim();
				let verifyCode = this.$data.verifyCode.trim();
				let accessToken = this.$data.accessToken.trim();
				if (phone == "" || password == "" || verifyCode == "" || accessToken == "") {
					/**
					 * uni.showToast(Object):用来和用户进行交互反馈的窗体
					 * title:必须设置，要提示用户的信息
					 * mask:设置之后用户不能触摸提示框以外的内容,默认为false
					 * icon:显示的反馈的图标
					 * duration:显示窗体的时间，单位是毫秒
					 */
					
					uni.showToast({
						title: this.$t('login.canNotNull'),
						mask: true,
						icon: 'error',
						duration: 2500
					});
					return false;
				}
				let requestData={
					phone:phone,
					password:password,
					verifyCode:verifyCode,
					accessToken:accessToken,
					areaCode:this.$data.currentArea
				};
				/**
				 * uni.showLoading(Object):显示加载的提示框
				 * title:显示的内容
				 */
				let loading=uni.showLoading({
					title:'loading'
				});
				uni.request({
					url:that.$data.baseURL+'/noAuth/user/register',
					method:'POST',
					data:requestData,
					header:{
						'Content-Type': 'application/x-www-form-urlencoded'
					},
					success(data) {
						let Qdatas=data.data;
						//如果请求成功，前端返回码为0，显示注册成功
						if(Qdatas.code==0){
							uni.showToast({
								title:that.$t('me.register.successInfo'),
								mask:true,
								duration:2000,
								success() {
									uni.redirectTo({
										url:'/pages/me/login'
									});
								}
							});
							return false;
						}
						uni.showToast({
							title:Qdatas.message,
							mask:true,
							icon:'error',
							duration:2000
						});
					},
					complete() {
						uni.hideLoading();
					}
				});
			},
			getVerifyCode() {
				let that=this;
				let phone= this.$data.phone.trim();
				if(phone==''){
					uni.showToast({
						title:this.$t('login.canNotNull'),
						mask:true,
						duration:2500,
						icon:'error'
					});
					return false;
				}
				let loading = uni.showLoading({
					title:'loading'
				});	
				uni.request({
					url:that.$data.baseURL+'/noAuth/utils/getVerifyCode',
					data:{
						phone:phone
					},
					success(data){
						let Qdata=data.data;
						that.$data.accessToken =Qdata.data;
						that.$data.submitdisabled = true;
						that.countDown();
						uni.showToast({
							title:"The verification code sent successfully",
							duration:2500,
							mask:true
						})
					},
					
					complete() {
						/*隐藏 loading 提示框。*/
						uni.hideLoading();
					}
			
				});
			},
			countDown(){
				
			}
		}
	}
</script>

<style>
</style>
