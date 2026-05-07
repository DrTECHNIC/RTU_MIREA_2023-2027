import { signIn } from '@/auth'

export default function LoginPage() {
    return (
        <div className="flex flex-col items-center gap-6">
            <h1 className="text-4xl font-bold">
                Авторизация
            </h1>
            <form action={async () => {
                'use server'
                await signIn('yandex', { redirectTo: '/profile', })
            }}>
                <button type="submit" className="rounded bg-black px-6 py-3 text-white">
                    Войти через Yandex
                </button>
            </form>
        </div>
    )
}
