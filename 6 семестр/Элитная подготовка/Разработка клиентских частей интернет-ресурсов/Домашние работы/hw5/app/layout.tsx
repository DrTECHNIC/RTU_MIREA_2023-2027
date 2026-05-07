import './globals.css'
import Navbar from '@/components/Navbar'

export default function RootLayout({ children, }:
    Readonly<{ children: React.ReactNode }>)
{
    return (
        <html lang="ru">
        <body>
        <Navbar />
        <main className="mx-auto min-h-screen max-w-5xl bg-zinc-50 px-4 py-6">
            {children}
        </main>
        </body>
        </html>
    )
}
