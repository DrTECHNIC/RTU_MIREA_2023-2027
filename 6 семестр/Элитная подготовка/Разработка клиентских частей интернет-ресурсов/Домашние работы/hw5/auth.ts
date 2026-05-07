import NextAuth from "next-auth"
import Yandex from "next-auth/providers/yandex"

export const { handlers, auth, signIn, signOut } = NextAuth({
    providers: [
        Yandex({
            clientId: process.env.AUTH_CLIENT_ID!,
            clientSecret: process.env.AUTH_CLIENT_SECRET!,
        }),
    ],
    secret: process.env.AUTH_SECRET,
})
