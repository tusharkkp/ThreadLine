package com.example.data

import com.example.model.*

object DemoDataProvider {

    val DEMO_WHATSAPP_RAW_TEXT = """
24/08/26, 09:15 AM - Messages and calls are end-to-end encrypted. No one outside of this chat, not even WhatsApp, can read or listen to them.
24/08/26, 09:30 AM - Tushar: Hey team! Welcome to the Smart India Hackathon internal group. Let's lock in the architecture today itself so we can start coding.
24/08/26, 09:35 AM - Shreyas: Yes! We only have 36 hours once the problem statements unlock. Let's divide tech stack and roles.
24/08/26, 09:42 AM - Rugweda: I can take up UI/UX design and pitch presentation deck.
24/08/26, 09:48 AM - Tejas: I will handle authentication and API integrations.
24/08/26, 09:55 AM - Shreyas: What frontend should we use? React Native or Native Android with Jetpack Compose?
24/08/26, 10:05 AM - Tushar: React Native thought kar raha tha for quick cross-platform, but Compose would be way smoother for native hardware sensors.
24/08/26, 10:12 AM - Tushar: Guys backend Firebase karu kya? Fast setup ho jayega.
24/08/26, 10:18 AM - Shreyas: Firebase works but PostgreSQL chahiye hume if we want relational joins for the smart city telemetry data.
24/08/26, 10:32 AM - Tushar: Okay let's go with Firebase for now. I'll create the Firebase project and share google-services.json.
24/08/26, 10:45 AM - Tejas: Great, I'm starting on the Firebase Auth module right away so we don't waste time.
24/08/26, 11:00 AM - Shreyas: For frontend, let's confirm: Native Android + Jetpack Compose with Material 3. React Native drops too many frames on old test devices.
24/08/26, 11:08 AM - Tushar: Confirmed! Frontend is Native Android + Jetpack Compose.
24/08/26, 11:30 AM - Rugweda: Designing the onboarding flow and Figma wireframes now. Will finish by Friday evening.
24/08/26, 12:15 PM - Tushar: @Tushar action: I'll prepare the initial project template and Gradle setup today.
24/08/26, 01:20 PM - Shreyas: What AI model should we use for incident categorization? Gemini API or local ONNX?
24/08/26, 01:45 PM - Tushar: Gemini API 1.5 Flash + on-device text token filtering. High accuracy and fast latency.
24/08/26, 02:07 PM - Rugweda: Supabase looks better for the DB guys. PostgreSQL direct mil jayega and realtime subscriptions are built-in without extra config.
24/08/26, 02:40 PM - Shreyas: Yeah, Supabase also gives us Row Level Security out of the box. Firebase Firestore queries will get messy for geo-spatial radius filters.
24/08/26, 03:15 PM - Tushar: Let's reconsider. Supabase will save us hours on complex SQL joins.
24/08/26, 04:10 PM - Shreyas: Agreed. Supabase better rahega.
24/08/26, 04:18 PM - Tushar: Done. Final: Supabase for backend and PostgreSQL database.
24/08/26, 04:30 PM - Tushar: @Tejas please switch the backend connection parameters to Supabase.
24/08/26, 05:02 PM - Tejas: Wait, Firebase auth ka basic module complete kar diya btw. Working with email login.
24/08/26, 05:30 PM - Rugweda: We also need to decide: Should authentication use Google OAuth or email/password or phone OTP?
24/08/26, 06:10 PM - Shreyas: Do we need offline support with Room SQLite caching when network drops at the venue?
24/08/26, 06:42 PM - Rugweda: Who will handle the cloud deployment and AWS/Vercel hosting for the presentation demo?
24/08/26, 07:15 PM - Tushar: For state management in Compose, we use ViewModel + Kotlin Coroutines/Flow. Confirmed.
24/08/26, 07:45 PM - Shreyas: Local persistence: Yes, Room DB is mandatory for offline caching. Confirmed.
24/08/26, 08:20 PM - Rugweda: Target audience for demo pitch: Municipal Corporation officers and field engineers. Confirmed.
24/08/26, 08:45 PM - Tushar: CI/CD Pipeline: GitHub Actions for automated APK builds on pull requests. Confirmed.
24/08/26, 09:10 PM - Tejas: Hardware test device: Pixel 7 and Samsung A54. Confirmed.
24/08/26, 09:30 PM - Shreyas: Minimum SDK level: Android 8.0 (API 26) to cover 95% devices. Confirmed.
24/08/26, 09:50 PM - Rugweda: Primary theme: Futuristic Dark Slate with Neon Indigo accents. Confirmed.
24/08/26, 10:15 PM - Tushar: Video demo recording deadline: Saturday 8:00 PM before final submission. Confirmed.
""".trimIndent()

    fun getSmartIndiaHackathonProject(): Project {
        val messages = parseDemoMessages()

        val decisions = listOf(
            Decision(
                id = "dec_backend",
                category = "Backend platform",
                title = "Backend & Database",
                currentValue = "Supabase (PostgreSQL)",
                previousValue = "Firebase",
                status = DecisionStatus.SUPERSEDED,
                decidedBy = "Tushar & Team",
                createdAt = "Aug 24, 10:32 AM",
                updatedAt = "Aug 24, 4:18 PM",
                participants = listOf("Tushar", "Shreyas", "Rugweda", "Tejas"),
                aiInsight = "Supabase appears to supersede the earlier Firebase decision, but the Firebase authentication work may still be active.",
                confidence = "High",
                isResolved = false,
                historyEvents = listOf(
                    DecisionHistoryEvent(
                        id = "ev_1",
                        timestamp = "Aug 24, 10:32 AM",
                        type = EventType.ORIGINAL_DECISION,
                        speaker = "Tushar",
                        quote = "Okay let's go with Firebase for now. I'll create the Firebase project and share google-services.json.",
                        contextNote = "Initial decision made for fast prototype kickoff."
                    ),
                    DecisionHistoryEvent(
                        id = "ev_2",
                        timestamp = "Aug 24, 2:07 PM",
                        type = EventType.DISCUSSION,
                        speaker = "Rugweda",
                        quote = "Supabase looks better for the DB guys. PostgreSQL direct mil jayega and realtime subscriptions are built-in.",
                        contextNote = "Team reassessed needs due to relational joins for smart city telemetry data."
                    ),
                    DecisionHistoryEvent(
                        id = "ev_3",
                        timestamp = "Aug 24, 4:18 PM",
                        type = EventType.UPDATED_DECISION,
                        speaker = "Tushar",
                        quote = "Done. Final: Supabase for backend and PostgreSQL database.",
                        contextNote = "Supabase officially selected as the primary backend platform."
                    ),
                    DecisionHistoryEvent(
                        id = "ev_4",
                        timestamp = "Aug 24, 5:02 PM",
                        type = EventType.POTENTIAL_CONFLICT,
                        speaker = "Tejas",
                        quote = "Wait, Firebase auth ka basic module complete kar diya btw. Working with email login.",
                        contextNote = "Conflict: Tejas implemented Firebase Auth before noticing or applying the Supabase decision."
                    )
                )
            ),
            Decision(
                id = "dec_frontend",
                category = "Frontend UI framework",
                title = "Mobile Application Framework",
                currentValue = "Native Android + Jetpack Compose",
                previousValue = "React Native",
                status = DecisionStatus.CONFIRMED,
                decidedBy = "Shreyas & Tushar",
                createdAt = "Aug 24, 10:05 AM",
                updatedAt = "Aug 24, 11:08 AM",
                participants = listOf("Shreyas", "Tushar"),
                aiInsight = "Team evaluated React Native for cross-platform, but pivoted to Native Compose for hardware sensor performance.",
                confidence = "High",
                isResolved = true,
                historyEvents = listOf(
                    DecisionHistoryEvent(
                        id = "ev_fe_1",
                        timestamp = "Aug 24, 10:05 AM",
                        type = EventType.ORIGINAL_DECISION,
                        speaker = "Tushar",
                        quote = "React Native thought kar raha tha for quick cross-platform.",
                        contextNote = "Initial proposal."
                    ),
                    DecisionHistoryEvent(
                        id = "ev_fe_2",
                        timestamp = "Aug 24, 11:00 AM",
                        type = EventType.DISCUSSION,
                        speaker = "Shreyas",
                        quote = "Native Android + Jetpack Compose with Material 3. React Native drops too many frames on test devices.",
                        contextNote = "Performance requirement raised."
                    ),
                    DecisionHistoryEvent(
                        id = "ev_fe_3",
                        timestamp = "Aug 24, 11:08 AM",
                        type = EventType.UPDATED_DECISION,
                        speaker = "Tushar",
                        quote = "Confirmed! Frontend is Native Android + Jetpack Compose.",
                        contextNote = "Confirmed choice."
                    )
                )
            ),
            Decision(
                id = "dec_ai",
                category = "AI model strategy",
                title = "Incident Categorization AI",
                currentValue = "Gemini API 1.5 Flash + on-device preprocessing",
                previousValue = null,
                status = DecisionStatus.CONFIRMED,
                decidedBy = "Tushar",
                createdAt = "Aug 24, 1:45 PM",
                updatedAt = "Aug 24, 1:45 PM",
                participants = listOf("Shreyas", "Tushar"),
                aiInsight = "Gemini 1.5 Flash provides optimal latency-cost trade-off for real-time hackathon inference.",
                confidence = "High",
                isResolved = true,
                historyEvents = listOf(
                    DecisionHistoryEvent(
                        id = "ev_ai_1",
                        timestamp = "Aug 24, 1:45 PM",
                        type = EventType.ORIGINAL_DECISION,
                        speaker = "Tushar",
                        quote = "Gemini API 1.5 Flash + on-device text token filtering. High accuracy and fast latency.",
                        contextNote = "Model strategy approved."
                    )
                )
            ),
            Decision(
                id = "dec_db",
                category = "Database Engine",
                title = "Relational Storage",
                currentValue = "Supabase PostgreSQL with RLS",
                previousValue = "Firestore NoSQL",
                status = DecisionStatus.CONFIRMED,
                decidedBy = "Shreyas & Rugweda",
                createdAt = "Aug 24, 4:18 PM",
                updatedAt = "Aug 24, 4:18 PM",
                participants = listOf("Rugweda", "Shreyas", "Tushar"),
                aiInsight = "PostgreSQL enables geo-spatial radius queries required for Smart City problem statement.",
                confidence = "High",
                isResolved = true,
                historyEvents = emptyList()
            ),
            Decision(
                id = "dec_offline",
                category = "Local Persistence",
                title = "Offline Cache Layer",
                currentValue = "Android Room SQLite",
                previousValue = null,
                status = DecisionStatus.CONFIRMED,
                decidedBy = "Shreyas",
                createdAt = "Aug 24, 7:45 PM",
                updatedAt = "Aug 24, 7:45 PM",
                participants = listOf("Shreyas"),
                aiInsight = "Offline mode made mandatory to protect against unreliable venue Wi-Fi during hackathon judging.",
                confidence = "High",
                isResolved = true,
                historyEvents = emptyList()
            ),
            Decision(
                id = "dec_state",
                category = "Architecture Pattern",
                title = "State Management",
                currentValue = "MVVM with StateFlow & Coroutines",
                previousValue = null,
                status = DecisionStatus.CONFIRMED,
                decidedBy = "Tushar",
                createdAt = "Aug 24, 7:15 PM",
                updatedAt = "Aug 24, 7:15 PM",
                participants = listOf("Tushar"),
                aiInsight = "Unidirectional Data Flow ensures UI reactivity without state desynchronization.",
                confidence = "High",
                isResolved = true,
                historyEvents = emptyList()
            ),
            Decision(
                id = "dec_theme",
                category = "Design System",
                title = "App Visual Identity",
                currentValue = "Futuristic Dark Slate with Neon Indigo accents",
                previousValue = null,
                status = DecisionStatus.CONFIRMED,
                decidedBy = "Rugweda",
                createdAt = "Aug 24, 9:50 PM",
                updatedAt = "Aug 24, 9:50 PM",
                participants = listOf("Rugweda"),
                aiInsight = "Dark theme matches hackathon presentation display screens and lowers OLED power consumption.",
                confidence = "High",
                isResolved = true,
                historyEvents = emptyList()
            ),
            Decision(
                id = "dec_target_users",
                category = "Product Scope",
                title = "Primary Pitch Stakeholder",
                currentValue = "Municipal Corporation Officers & Field Engineers",
                previousValue = null,
                status = DecisionStatus.CONFIRMED,
                decidedBy = "Rugweda",
                createdAt = "Aug 24, 8:20 PM",
                updatedAt = "Aug 24, 8:20 PM",
                participants = listOf("Rugweda"),
                aiInsight = "Framing the pitch around municipal staff highlights clear B2G enterprise utility.",
                confidence = "High",
                isResolved = true,
                historyEvents = emptyList()
            ),
            Decision(
                id = "dec_cicd",
                category = "DevOps & Tooling",
                title = "Continuous Integration",
                currentValue = "GitHub Actions APK Build Pipeline",
                previousValue = null,
                status = DecisionStatus.CONFIRMED,
                decidedBy = "Tushar",
                createdAt = "Aug 24, 8:45 PM",
                updatedAt = "Aug 24, 8:45 PM",
                participants = listOf("Tushar"),
                aiInsight = "Automated debug APK build prevents local compile disparities before judge review.",
                confidence = "High",
                isResolved = true,
                historyEvents = emptyList()
            ),
            Decision(
                id = "dec_test_devices",
                category = "Testing Strategy",
                title = "Hardware Benchmark Devices",
                currentValue = "Google Pixel 7 and Samsung A54",
                previousValue = null,
                status = DecisionStatus.CONFIRMED,
                decidedBy = "Tejas",
                createdAt = "Aug 24, 9:10 PM",
                updatedAt = "Aug 24, 9:10 PM",
                participants = listOf("Tejas"),
                aiInsight = "Verifies performance across both Tensor and Exynos chips.",
                confidence = "High",
                isResolved = true,
                historyEvents = emptyList()
            ),
            Decision(
                id = "dec_minsdk",
                category = "Compatibility",
                title = "Minimum Android Version",
                currentValue = "Android 8.0 (API 26)",
                previousValue = null,
                status = DecisionStatus.CONFIRMED,
                decidedBy = "Shreyas",
                createdAt = "Aug 24, 9:30 PM",
                updatedAt = "Aug 24, 9:30 PM",
                participants = listOf("Shreyas"),
                aiInsight = "API 26 enables modern Java 8+ APIs while covering ~95% active Android devices in India.",
                confidence = "High",
                isResolved = true,
                historyEvents = emptyList()
            ),
            Decision(
                id = "dec_video_deadline",
                category = "Submission Milestone",
                title = "Demo Video Cut-off",
                currentValue = "Saturday 8:00 PM",
                previousValue = null,
                status = DecisionStatus.CONFIRMED,
                decidedBy = "Tushar",
                createdAt = "Aug 24, 10:15 PM",
                updatedAt = "Aug 24, 10:15 PM",
                participants = listOf("Tushar"),
                aiInsight = "Leaves 4-hour buffer before the 11:59 PM SIH portal freeze.",
                confidence = "High",
                isResolved = true,
                historyEvents = emptyList()
            )
        )

        val changes = listOf(
            DecisionChange(
                id = "ch_1",
                decisionId = "dec_backend",
                title = "Backend platform change",
                originalValue = "Firebase",
                newValue = "Supabase",
                originalTimestamp = "Aug 24, 10:32 AM",
                updatedTimestamp = "Aug 24, 4:18 PM",
                reason = "Team required relational joins and geo-spatial queries for telemetry data that Firestore could not efficiently provide.",
                decidedBy = "Tushar & Shreyas",
                confidence = "High",
                hasConflict = true,
                conflictDescription = "Firebase auth module was completed before team switched to Supabase."
            ),
            DecisionChange(
                id = "ch_2",
                decisionId = "dec_frontend",
                title = "UI Framework change",
                originalValue = "React Native",
                newValue = "Native Android (Jetpack Compose)",
                originalTimestamp = "Aug 24, 10:05 AM",
                updatedTimestamp = "Aug 24, 11:08 AM",
                reason = "React Native had potential frame drops and complicated sensor bridging for hardware telemetry.",
                decidedBy = "Shreyas & Tushar",
                confidence = "High",
                hasConflict = false
            )
        )

        val conflicts = listOf(
            Conflict(
                id = "conf_1",
                relatedDecisionId = "dec_backend",
                relatedDecisionTitle = "Backend platform",
                description = "Firebase authentication appears implemented despite the current Supabase decision.",
                quote = "Wait, Firebase auth ka basic module complete kar diya btw. Working with email login.",
                author = "Tejas",
                timestamp = "Aug 24, 5:02 PM",
                severity = ConflictSeverity.HIGH,
                suggestedAction = "Decide whether to migrate Tejas's auth flow to Supabase GoTrue Auth or keep Firebase Auth via custom JWT bridge.",
                isResolved = false
            )
        )

        val actions = listOf(
            ActionItem(
                id = "act_1",
                owner = "Tushar",
                task = "Prepare initial project template & Gradle setup",
                deadline = "Today",
                status = ActionStatus.IN_PROGRESS,
                sourceQuote = "@Tushar action: I'll prepare the initial project template and Gradle setup today.",
                timestamp = "Aug 24, 12:15 PM",
                isMyTask = true
            ),
            ActionItem(
                id = "act_2",
                owner = "Tejas",
                task = "Implement authentication & resolve Supabase/Firebase conflict",
                deadline = "Tomorrow",
                status = ActionStatus.IN_PROGRESS,
                sourceQuote = "Wait, Firebase auth ka basic module complete kar diya btw.",
                timestamp = "Aug 24, 5:02 PM"
            ),
            ActionItem(
                id = "act_3",
                owner = "Rugweda",
                task = "Design onboarding flow & Figma wireframes",
                deadline = "Friday evening",
                status = ActionStatus.PENDING,
                sourceQuote = "Designing the onboarding flow and Figma wireframes now. Will finish by Friday evening.",
                timestamp = "Aug 24, 11:30 AM"
            ),
            ActionItem(
                id = "act_4",
                owner = "Shreyas",
                task = "Set up Room SQLite database entities and DAO caching",
                deadline = "Friday morning",
                status = ActionStatus.PENDING,
                sourceQuote = "Local persistence: Yes, Room DB is mandatory for offline caching.",
                timestamp = "Aug 24, 7:45 PM"
            ),
            ActionItem(
                id = "act_5",
                owner = "Tushar",
                task = "Integrate Gemini 1.5 Flash API client with token filters",
                deadline = "Friday afternoon",
                status = ActionStatus.PENDING,
                sourceQuote = "Gemini API 1.5 Flash + on-device text token filtering.",
                timestamp = "Aug 24, 1:45 PM",
                isMyTask = true
            ),
            ActionItem(
                id = "act_6",
                owner = "Rugweda",
                task = "Prepare final pitch deck for jury presentation",
                deadline = "Saturday afternoon",
                status = ActionStatus.PENDING,
                sourceQuote = "I can take up UI/UX design and pitch presentation deck.",
                timestamp = "Aug 24, 09:42 AM"
            ),
            ActionItem(
                id = "act_7",
                owner = "Tushar",
                task = "Record and cut final 3-minute hackathon product demo video",
                deadline = "Saturday 8:00 PM",
                status = ActionStatus.PENDING,
                sourceQuote = "Video demo recording deadline: Saturday 8:00 PM before final submission.",
                timestamp = "Aug 24, 10:15 PM",
                isMyTask = true
            )
        )

        val openQuestions = listOf(
            OpenQuestion(
                id = "q_1",
                question = "Should authentication use Google OAuth, email/password, or phone OTP?",
                askedBy = "Rugweda",
                timestamp = "Aug 24, 5:30 PM",
                contextQuote = "We also need to decide: Should authentication use Google OAuth or email/password or phone OTP?",
                isResolved = false
            ),
            OpenQuestion(
                id = "q_2",
                question = "Who will handle cloud deployment & AWS/Vercel hosting for demo servers?",
                askedBy = "Rugweda",
                timestamp = "Aug 24, 6:42 PM",
                contextQuote = "Who will handle the cloud deployment and AWS/Vercel hosting for the presentation demo?",
                isResolved = false
            ),
            OpenQuestion(
                id = "q_3",
                question = "Do we need offline support with Room SQLite caching when venue network drops?",
                askedBy = "Shreyas",
                timestamp = "Aug 24, 6:10 PM",
                contextQuote = "Do we need offline support with Room SQLite caching when network drops at the venue?",
                isResolved = true,
                resolutionNote = "Resolved at 7:45 PM: Room DB confirmed for offline caching."
            ),
            OpenQuestion(
                id = "q_4",
                question = "How will mock emergency alert triggers be simulated in front of judges?",
                askedBy = "Tejas",
                timestamp = "Aug 24, 8:05 PM",
                contextQuote = "Judges might test without real hardware, should we build a debug trigger drawer?",
                isResolved = false
            )
        )

        val techStack = listOf(
            TechStackItem("Backend", "Supabase", "Confirmed", "dec_backend"),
            TechStackItem("Frontend", "Android + Jetpack Compose", "Confirmed", "dec_frontend"),
            TechStackItem("AI Strategy", "Gemini API + On-device", "Confirmed", "dec_ai"),
            TechStackItem("Database", "Supabase PostgreSQL", "Confirmed", "dec_db"),
            TechStackItem("Authentication", "In Conflict (Firebase vs Supabase)", "Conflict flagged", "dec_backend"),
            TechStackItem("Local Cache", "Android Room SQLite", "Confirmed", "dec_offline")
        )

        val activities = listOf(
            ActivityTimelineItem(
                id = "act_ev_1",
                timestamp = "Today, 5:02 PM",
                title = "Potential conflict detected",
                description = "Tejas finished Firebase auth while current backend decision is Supabase.",
                author = "Tejas",
                type = ActivityType.CONFLICT_DETECTED,
                referenceId = "conf_1"
            ),
            ActivityTimelineItem(
                id = "act_ev_2",
                timestamp = "Today, 4:18 PM",
                title = "Decision changed",
                description = "Backend platform: Firebase → Supabase",
                author = "Tushar",
                type = ActivityType.DECISION_CHANGED,
                referenceId = "dec_backend"
            ),
            ActivityTimelineItem(
                id = "act_ev_3",
                timestamp = "Today, 1:45 PM",
                title = "Decision confirmed",
                description = "AI Incident Categorization: Gemini API 1.5 Flash",
                author = "Tushar",
                type = ActivityType.DECISION_CONFIRMED,
                referenceId = "dec_ai"
            ),
            ActivityTimelineItem(
                id = "act_ev_4",
                timestamp = "Today, 12:15 PM",
                title = "Action assigned",
                description = "Tushar → Prepare initial project template & Gradle setup",
                author = "Tushar",
                type = ActivityType.ACTION_ASSIGNED,
                referenceId = "act_1"
            ),
            ActivityTimelineItem(
                id = "act_ev_5",
                timestamp = "Today, 11:08 AM",
                title = "Decision changed",
                description = "Frontend Framework: React Native → Native Android Compose",
                author = "Shreyas",
                type = ActivityType.DECISION_CHANGED,
                referenceId = "dec_frontend"
            ),
            ActivityTimelineItem(
                id = "act_ev_6",
                timestamp = "Today, 9:48 AM",
                title = "Question opened",
                description = "Should authentication use Google OAuth or phone OTP?",
                author = "Rugweda",
                type = ActivityType.QUESTION_OPENED,
                referenceId = "q_1"
            )
        )

        return Project(
            id = "proj_sih_2026",
            name = "Smart India Hackathon",
            lastAnalyzed = "Today, 6:42 PM",
            rawChatContent = DEMO_WHATSAPP_RAW_TEXT,
            messagesCount = messages.size,
            decisionsCount = decisions.size,
            openQuestionsCount = openQuestions.filter { !it.isResolved }.size,
            actionItemsCount = actions.size,
            changedDecisionsCount = changes.size,
            potentialConflictsCount = conflicts.filter { !it.isResolved }.size,
            techStack = techStack,
            decisions = decisions,
            actions = actions,
            openQuestions = openQuestions,
            changes = changes,
            conflicts = conflicts,
            activities = activities,
            messages = messages
        )
    }

    fun getSecondaryProject(): Project {
        return Project(
            id = "proj_android_mini",
            name = "Android Mini Project",
            lastAnalyzed = "Yesterday, 4:15 PM",
            rawChatContent = "Sample mini project chat...",
            messagesCount = 28,
            decisionsCount = 5,
            openQuestionsCount = 2,
            actionItemsCount = 3,
            changedDecisionsCount = 1,
            potentialConflictsCount = 0,
            techStack = listOf(
                TechStackItem("Frontend", "Jetpack Compose", "Confirmed", "dec_m1"),
                TechStackItem("Local Storage", "Room DB", "Confirmed", "dec_m2"),
                TechStackItem("Network", "Retrofit + OkHttp", "Confirmed", "dec_m3")
            ),
            decisions = listOf(
                Decision(
                    id = "dec_m1",
                    category = "Architecture",
                    title = "Application Architecture",
                    currentValue = "Clean Architecture + MVVM",
                    previousValue = "MVC",
                    status = DecisionStatus.CONFIRMED,
                    decidedBy = "Team",
                    createdAt = "Yesterday, 2:00 PM",
                    updatedAt = "Yesterday, 3:30 PM",
                    participants = listOf("Aditya", "Tushar"),
                    aiInsight = "Refactored from MVC to MVVM for testability.",
                    confidence = "High",
                    historyEvents = emptyList()
                )
            ),
            actions = listOf(
                ActionItem(
                    id = "act_m1",
                    owner = "Tushar",
                    task = "Write unit tests for ViewModel",
                    deadline = "Sunday",
                    status = ActionStatus.IN_PROGRESS,
                    sourceQuote = "I'll cover the ViewModel tests.",
                    timestamp = "Yesterday, 3:45 PM",
                    isMyTask = true
                )
            ),
            openQuestions = listOf(
                OpenQuestion(
                    id = "q_m1",
                    question = "Should we include dark mode toggle in settings?",
                    askedBy = "Aditya",
                    timestamp = "Yesterday, 4:00 PM",
                    contextQuote = "Dark mode support?",
                    isResolved = false
                )
            ),
            changes = listOf(
                DecisionChange(
                    id = "ch_m1",
                    decisionId = "dec_m1",
                    title = "Architecture Refactoring",
                    originalValue = "MVC",
                    newValue = "MVVM",
                    originalTimestamp = "Yesterday, 2:00 PM",
                    updatedTimestamp = "Yesterday, 3:30 PM",
                    reason = "Decided MVVM integrates better with Compose State.",
                    decidedBy = "Team"
                )
            ),
            conflicts = emptyList(),
            activities = listOf(
                ActivityTimelineItem(
                    id = "act_m_ev1",
                    timestamp = "Yesterday, 3:30 PM",
                    title = "Decision changed",
                    description = "Architecture: MVC → MVVM",
                    author = "Aditya",
                    type = ActivityType.DECISION_CHANGED
                )
            ),
            messages = emptyList()
        )
    }

    private fun parseDemoMessages(): List<Message> {
        val lines = DEMO_WHATSAPP_RAW_TEXT.lines()
        val result = mutableListOf<Message>()
        var idCounter = 1

        val regex = Regex("""^(\d{2}/\d{2}/\d{2},\s+\d{1,2}:\d{2}\s+(?:AM|PM))\s+-\s+([^:]+):\s+(.*)$""")

        for (line in lines) {
            val match = regex.find(line)
            if (match != null) {
                val (timeStr, sender, text) = match.destructured
                val tag = when {
                    text.contains("karu kya", ignoreCase = true) || text.contains("let's go with Firebase", ignoreCase = true) -> MessageTag.DECISION_ORIGIN
                    text.contains("Supabase", ignoreCase = true) && text.contains("Final", ignoreCase = true) -> MessageTag.DECISION_UPDATE
                    text.contains("Firebase auth ka basic module complete", ignoreCase = true) -> MessageTag.CONFLICT
                    text.contains("action:", ignoreCase = true) || text.contains("Designing the onboarding", ignoreCase = true) -> MessageTag.ACTION_ITEM
                    text.contains("?", ignoreCase = true) -> MessageTag.OPEN_QUESTION
                    else -> MessageTag.DISCUSSION
                }
                result.add(
                    Message(
                        id = "msg_${idCounter++}",
                        sender = sender.trim(),
                        text = text.trim(),
                        timestamp = timeStr.substringAfter(", ").trim(),
                        tag = tag
                    )
                )
            }
        }
        return result
    }

    /**
     * Pre-compiled smart QA knowledge base for instant answers with decision evolution explanation.
     */
    fun answerProjectQuestion(query: String, project: Project): String {
        val q = query.trim().lowercase()
        return when {
            q.contains("backend") || q.contains("database") || q.contains("supabase") || q.contains("firebase") -> {
                """Your current backend decision is Supabase (with PostgreSQL).

• Original decision: Firebase at 10:32 AM (proposed by Tushar for rapid prototyping).
• Updated decision: Supabase at 4:18 PM because the team required relational joins and PostgreSQL geo-spatial queries.

⚠️ Potential conflict: Tejas finished the Firebase Auth module at 5:02 PM before switching to Supabase. This auth module needs to either be migrated to Supabase GoTrue or bridged."""
            }
            q.contains("tech stack") || q.contains("stack") || q.contains("architecture") -> {
                """Current Confirmed Tech Stack for ${project.name}:

• Frontend: Native Android + Jetpack Compose (Material 3)
• Backend: Supabase (PostgreSQL with Row Level Security)
• AI Engine: Gemini API 1.5 Flash + on-device token filters
• Local Cache: Android Room SQLite for offline mode
• Architecture: MVVM with Kotlin StateFlow & Coroutines

Note: 1 active conflict detected regarding Firebase Auth integration."""
            }
            q.contains("unresolved") || q.contains("open") || q.contains("question") -> {
                """There are ${project.openQuestions.count { !it.isResolved }} unresolved questions:

1. Authentication Method: Should we use Google OAuth, email/password, or phone OTP? (Asked by Rugweda at 5:30 PM)
2. Cloud Deployment: Who will handle cloud deployment & AWS/Vercel hosting for the presentation? (Asked by Rugweda at 6:42 PM)
3. Mock Triggers: How will mock emergency alerts be simulated during judge review? (Asked by Tejas at 8:05 PM)"""
            }
            q.contains("my task") || q.contains("i need to do") || q.contains("tushar") || q.contains("todo") || q.contains("finish") -> {
                """Tasks assigned to Tushar:

1. [In Progress] Prepare initial project template & Gradle setup (Due: Today)
2. [Pending] Integrate Gemini 1.5 Flash API client with token filters (Due: Friday afternoon)
3. [Pending] Record & cut final 3-minute hackathon demo video (Due: Saturday 8:00 PM)"""
            }
            q.contains("change") || q.contains("yesterday") || q.contains("pivot") -> {
                """2 major decision changes were detected in the conversation:

1. Backend Platform: Firebase → Supabase (Reason: Needed PostgreSQL joins and geo-spatial queries)
2. UI Framework: React Native → Native Android Jetpack Compose (Reason: Prevent frame drops on older test hardware and direct sensor access)"""
            }
            else -> {
                """Based on project memory for ${project.name}:

The team has locked in 12 core decisions, including Native Android Compose, Supabase PostgreSQL, and Gemini 1.5 Flash.

Key attention item: Resolve the Firebase authentication conflict with Tejas before final deployment."""
            }
        }
    }
}
