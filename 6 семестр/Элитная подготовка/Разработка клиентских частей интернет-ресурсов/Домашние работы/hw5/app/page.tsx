export default function HomePage() {
    return (
        <div className="space-y-10">
            <section className="rounded-xl bg-white p-6 shadow-sm">
                <h1 className="text-3xl font-bold text-zinc-900">
                    Блог о языках программирования и компьютерных системах
                </h1>
                <p className="mt-4 text-zinc-600">
                    Этот проект представляет собой учебный блог, посвящённый современным языкам программирования и фундаментальным концепциям компьютерных наук.
                </p>
                <p className="mt-3 text-zinc-600">
                    Здесь публикуются материалы о JavaScript, Python, C++, C#, Java, а также о сетевых технологиях, архитектуре программного обеспечения и алгоритмах.
                </p>
            </section>
            <section className="grid gap-4 md:grid-cols-2">
                <div className="rounded-xl border bg-zinc-50 p-5">
                    <h2 className="text-lg font-semibold">
                        Основные направления
                    </h2>
                    <p className="mt-2 text-sm text-zinc-600">
                        Языки программирования (C++, C#, Java, Python, JavaScript), системное программирование и основы архитектуры ПО.
                    </p>
                </div>
                <div className="rounded-xl border bg-zinc-50 p-5">
                    <h2 className="text-lg font-semibold">
                        Тематика материалов
                    </h2>
                    <p className="mt-2 text-sm text-zinc-600">
                        Компьютерные сети (HTTP, TCP/IP), алгоритмы, структуры данных, управление памятью и виртуальные машины.
                    </p>
                </div>
                <div className="rounded-xl border bg-zinc-50 p-5 md:col-span-2">
                    <h2 className="text-lg font-semibold">
                        Формат блога
                    </h2>
                    <p className="mt-2 text-sm text-zinc-600">
                        Короткие и расширенные статьи в стиле технических справок, основанные на общепринятых определениях и концепциях из компьютерных наук.
                    </p>
                </div>
            </section>
        </div>
    )
}
