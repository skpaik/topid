import NextAuth, {NextAuthOptions} from "next-auth"
import GoogleProvider from "next-auth/providers/google"
import FacebookProvider from "next-auth/providers/facebook"
import GithubProvider from "next-auth/providers/github"
import TwitterProvider from "next-auth/providers/twitter"
import Auth0Provider from "next-auth/providers/auth0"
import {AuthService} from "../../../app/services/authService";
// import AppleProvider from "next-auth/providers/apple"
// import {MongoDBAdapter} from "@next-auth/mongodb-adapter"
// import clientPromise from "../../../lib/mongodb"
// For more information on each option (and a full list of options) go to
// https://next-auth.js.org/configuration/options
export const authOptions: NextAuthOptions = {
    // https://next-auth.js.org/configuration/providers/oauth
    providers: [
        // CredentialsProvider({
        //     // The name to display on the sign in form (e.g. "Sign in with...")
        //     name: "Credentials",
        //     // `credentials` is used to generate a form on the sign in page.
        //     // You can specify which fields should be submitted, by adding keys to the `credentials` object.
        //     // e.g. domain, username, password, 2FA token, etc.
        //     // You can pass any HTML attribute to the <input> tag through the object.
        //     credentials: {
        //         username: { label: "Username", type: "text", placeholder: "jsmith" },
        //         password: { label: "Password", type: "password" }
        //     },
        //     async authorize(credentials, req) {
        //         // Add logic here to look up the user from the credentials supplied
        //         const user = { id: "1", name: "J Smith", email: "jsmith@example.com" }
        //         console.log(credentials)
        //         console.log(req)
        //         if (user) {
        //             // Any object returned will be saved in `user` property of the JWT
        //             return user
        //         } else {
        //             // If you return null then an error will be displayed advising the user to check their details.
        //             return null
        //
        //             // You can also Reject this callback with an Error thus the user will be sent to the error page with the error message as a query parameter
        //         }
        //     }
        // }),

        /*
        EmailProvider({
                    server: {
                        host: process.env.EMAIL_SERVER_HOST,
                        port: process.env.EMAIL_SERVER_PORT,
                        auth: {
                            user: process.env.EMAIL_SERVER_USER,
                            pass: process.env.EMAIL_SERVER_PASSWORD,
                        },
                    },
                    from: process.env.EMAIL_FROM,
                    maxAge: 24 * 60 * 60,
                }),
                */
        // Temporarily removing the Apple provider from the demo site as the
        // callback URL for it needs updating due to Vercel changing domains
        /*
           Providers.Apple({
             clientId: process.env.APPLE_ID,
             clientSecret: {
               appleId: process.env.APPLE_ID,
               teamId: process.env.APPLE_TEAM_ID,
               privateKey: process.env.APPLE_PRIVATE_KEY,
               keyId: process.env.APPLE_KEY_ID,
             },
           }),
           */
        FacebookProvider({
            clientId: process.env.FACEBOOK_ID,
            clientSecret: process.env.FACEBOOK_SECRET,
        }),
        GithubProvider({
            clientId: process.env.GITHUB_ID,
            clientSecret: process.env.GITHUB_SECRET,
        }),
        GoogleProvider({
            clientId: process.env.GOOGLE_ID,
            clientSecret: process.env.GOOGLE_SECRET,
        }),
        TwitterProvider({
            clientId: process.env.TWITTER_ID,
            clientSecret: process.env.TWITTER_SECRET,
        }),
        Auth0Provider({
            clientId: process.env.AUTH0_ID,
            clientSecret: process.env.AUTH0_SECRET,
            issuer: process.env.AUTH0_ISSUER,
        }),
    ],
    theme: {
        colorScheme: "auto", // "auto" | "dark" | "light"
        brandColor: "", // Hex color code
        logo: "", // Absolute URL to image
        buttonText: "" // Hex color code
    },
    callbacks: {
        async signIn({
                         user,
                         account,
                         profile,
                         email,
                         credentials
                     }) {

            console.log('async signIn')
            console.log('user', user)
            console.log('account', account)
            console.log('profile', profile)
            console.log('email', email)
            console.log('credentials', credentials)
            new AuthService().registerGithubUser(user, account, profile);

            const isAllowedToSignIn = true
            if (isAllowedToSignIn) {
                return true
            } else {
                // Return false to display a default error message
                return false
                // Or you can return a URL to redirect to:
                // return '/unauthorized'
            }
        },
        async jwt({token, account, profile}) {
            console.log('async jwt')
            console.log('token', token)
            console.log('account', account)
            console.log('profile', profile)

            // Persist the OAuth access_token and or the user id to the token right after signin
            // Persist the OAuth access_token and or the user id to the token right after signin

            if (account) {
                token.accessToken = account.access_token
                // @ts-ignore
                token.id = profile.id
            }
            token.userRole = "admin"

            return token
        },
        async session({session, token, user}) {
            // Send properties to the client, like an access_token and user id from a provider.
            // @ts-ignore
            //session.accessToken = token.accessToken
            // @ts-ignore
            //session.user.id = token.id

            //return session

            session.userRole = token.userRole;

            console.log('async session')
            console.log('session', session)
            console.log('token', token)
            console.log('user', user)

            return session
        },
        async redirect({url, baseUrl}) {
            console.log('async redirect')
            console.log('url', url)
            console.log('baseUrl', baseUrl)
            // Allows relative callback URLs
            if (url.startsWith("/")) return `${baseUrl}${url}`
            // Allows callback URLs on the same origin
            else if (new URL(url).origin === baseUrl) return url

            return baseUrl
        }
    },
    //adapter: MongoDBAdapter(clientPromise),
    // use env variable in production
    secret: process.env.NEXTAUTH_SECRET,
    events: {
        async signIn(message) { /* on successful sign in */
        },
        async signOut(message) { /* on signout */
        },
        async createUser(message) { /* user created */
        },
        async updateUser(message) { /* user updated - e.g. their email was verified */
        },
        async linkAccount(message) { /* account (e.g. Twitter) linked to a user */
        },
        async session(message) { /* session is active */
        },
    }
}

export default NextAuth(authOptions)
