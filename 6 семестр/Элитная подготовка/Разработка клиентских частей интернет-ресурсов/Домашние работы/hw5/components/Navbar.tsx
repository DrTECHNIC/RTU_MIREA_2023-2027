import Link from 'next/link'
import { auth } from '@/auth'

export default async function Navbar() {
    const session = await auth()

    return (
        <nav className="sticky top-0 z-50 border-b border-zinc-800 bg-zinc-900/90 text-zinc-100 backdrop-blur">
            <div className="mx-auto flex max-w-5xl flex-col gap-3 px-4 py-4 md:flex-row md:items-center md:justify-between">
                <div className="flex items-center gap-5">
                    <Link href="/" className="text-lg font-semibold tracking-tight">
                        TechBlog
                    </Link>
                    <div className="flex gap-4 text-sm md:text-base">
                        <Link href="/posts" className="hover:text-white">
                            Посты
                        </Link>
                    </div>
                </div>
                <div className="text-sm md:text-base">
                    {session?.user ? (
                        <Link href="/profile" className="rounded bg-zinc-700 px-3 py-1 hover:bg-zinc-600">
                            Профиль
                        </Link>
                    ) : (
                        <Link href="/login" className="rounded bg-zinc-700 px-3 py-1 hover:bg-zinc-600">
                            Войти
                        </Link>
                    )}
                </div>
            </div>
        </nav>
    )
}
