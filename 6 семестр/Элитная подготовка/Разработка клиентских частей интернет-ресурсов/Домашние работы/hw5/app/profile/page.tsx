import { auth, signOut } from '@/auth'
import { redirect } from 'next/navigation'
import Image from 'next/image'

export default async function ProfilePage() {
    const session = await auth()
    if (!session?.user) redirect('/login')
    const user = session.user

    return (
        <div className="mx-auto max-w-2xl rounded-xl border bg-white p-6 shadow-sm">
            <h1 className="mb-6 text-2xl font-bold">
                Профиль пользователя
            </h1>
            <div className="flex items-center gap-4">
                {user.image && (<Image src={user.image} alt="avatar" width={80} height={80} className="rounded-full"/>)}
                <div>
                    <div className="text-xl font-semibold">
                        {user.name || 'Без имени'}
                    </div>
                    <div className="text-sm text-zinc-500">
                        OAuth через Yandex
                    </div>
                </div>
            </div>
            <div className="mt-6 grid gap-3">
                <div className="rounded border bg-zinc-50 p-3">
                    <div className="text-xs text-zinc-500">
                        Имя
                    </div>
                    <div className="text-sm font-medium">
                        {user.name || '—'}
                    </div>
                </div>
                <div className="rounded border bg-zinc-50 p-3">
                    <div className="text-xs text-zinc-500">
                        Email
                    </div>
                    <div className="text-sm font-medium">
                        {user.email || '—'}
                    </div>
                </div>
                <div className="rounded border bg-zinc-50 p-3">
                    <div className="text-xs text-zinc-500">
                        Изображение профиля
                    </div>
                    <div className="text-sm break-all">
                        {user.image || '—'}
                    </div>
                </div>
            </div>
            <details className="mt-6">
                <summary className="cursor-pointer text-sm text-blue-600">
                    Показать OAuth данные
                </summary>
                <pre className="mt-2 overflow-auto rounded bg-zinc-900 p-3 text-xs text-white">
                    {JSON.stringify(session, null, 2)}
                </pre>
            </details>
            <form action={async () => {
                'use server'
                await signOut({ redirectTo: '/' })
            }} className="mt-6">
                <button className="rounded bg-red-500 px-4 py-2 text-white">
                    Выйти
                </button>
            </form>
        </div>
    )
}
