import { defineStore } from 'pinia'
import api from '@/utils/api'

interface User {
  authenticated: boolean
  name: string
  email?: string
  picture?: string
  authorities: string[]
}

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null as User | null,
    loading: false,
    error: null as string | null
  }),

  getters: {
    isAuthenticated: (state) => state.user?.authenticated ?? false,
    isOps: (state) => state.user?.authorities.includes('ROLE_OPS') ?? false,
    isAdmin: (state) => state.user?.authorities.includes('ROLE_ADMIN') ?? false
  },

  actions: {
    async fetchUser() {
      this.loading = true
      this.error = null
      try {
        const response = await api.get('/api/me')
        if (response.data.authenticated) {
          this.user = response.data
        } else {
          this.user = null
        }
      } catch (err: any) {
        console.error('Failed to fetch user:', err)
        this.user = null
        if (err.response?.status !== 401) {
          this.error = 'Failed to load user information'
        }
      } finally {
        this.loading = false
      }
    },

    loginWithGoogle() {
      window.location.href = '/oauth2/authorization/google'
    },

    loginWithGitHub() {
      window.location.href = '/oauth2/authorization/github'
    },

      async loginWithAd(username: string, password: string) {
        this.loading = true
        this.error = null
        try {
          // Form-Data senden (Standard für Spring Security Form Login)
          const params = new URLSearchParams()
          params.append('username', username)
          params.append('password', password)

          await api.post('/api/login', params, {
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
          })
          
          // Nach Login direkt User laden
          await this.fetchUser()
        } catch (err: any) {
          console.error('AD Login failed:', err)
          this.error = 'Invalid username or password'
          throw err
        } finally {
          this.loading = false
        }
      },


    async logout() {
      try {
        await api.post('/api/logout')
      } catch (err) {
        console.error('Logout failed:', err)
      }
      this.user = null
      window.location.href = '/'
    }
  }
})
