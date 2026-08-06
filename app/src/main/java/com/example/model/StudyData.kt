package com.example.model

data class Subject(
  val id: String,
  val name: String,
  val chapters: List<Chapter>
)

data class Chapter(
  val id: String,
  val title: String,
  val subtitle: String,
  val textContent: List<String>,
  val flashcards: List<Flashcard>,
  val quizQuestions: List<QuizQuestion>,
  val isPremium: Boolean = false
)

enum class SrsStage(val label: String) {
  NEW("New"),
  DUE("Due Today"),
  LEARNING("Learning"),
  MASTERED("Mastered")
}

data class Flashcard(
  val id: String,
  val question: String,
  val answer: String,
  val category: String,
  var userRating: CardRating? = null,
  var nextReviewDays: Int = 0,
  var stage: SrsStage = SrsStage.DUE
)

enum class CardRating {
  EASY, GOOD, HARD
}

data class QuizQuestion(
  val id: String,
  val questionText: String,
  val options: List<String>,
  val correctAnswerIndex: Int,
  val explanation: String
)

object StudyRepository {
  val subjects: List<Subject> = listOf(
    // ==========================================
    // 1. INDIAN POLITY (M. Laxmikanth)
    // ==========================================
    Subject(
      id = "polity",
      name = "Indian Polity",
      chapters = listOf(
        Chapter(
          id = "pol_ch1",
          title = "Ch 1: Historical Background",
          subtitle = "Regulating Act 1773 to Indian Independence Act 1947",
          isPremium = false,
          textContent = listOf(
            "==================================================",
            "PART 1: HISTORICAL TIMELINE & CORE PROVISIONS",
            "==================================================",
            "The legal and constitutional framework of India evolved through two distinct phases of British rule:",
            "A. THE COMPANY RULE (1773–1858)",
            "1. Regulating Act of 1773:",
            "   • First step taken by British Government to control and regulate the affairs of East India Company (EIC) in India.",
            "   • Recognized political & administrative functions of EIC.",
            "   • Designated Governor of Bengal as 'Governor-General of Bengal' (Lord Warren Hastings) with an Executive Council of 4 members.",
            "   • Subordinated Governors of Bombay and Madras to Governor-General of Bengal.",
            "   • Established Supreme Court at Calcutta (1774) with 1 Chief Justice (Sir Elijah Impey) and 3 other judges.",
            "   • Prohibited Company servants from engaging in private trade or accepting presents/bribes.",
            "2. Pitt's India Act of 1784:",
            "   • Distinguished between Commercial and Political functions of Company.",
            "   • Court of Directors managed commercial affairs; created 'Board of Control' (6 members) to manage political affairs (System of Double Government).",
            "   • Empowered Board of Control to supervise all civil, military, and revenue operations of 'British possessions in India' (first time this phrase was used).",
            "3. Charter Act of 1833:",
            "   • Final step towards centralisation in British India.",
            "   • Designated Governor-General of Bengal as 'Governor-General of India' (Lord William Bentinck) vested with all civil and military powers.",
            "   • Deprived Bombay and Madras Governors of legislative powers; GG of India given exclusive legislative powers for all British India.",
            "   • Ended EIC commercial activity completely; EIC became a purely administrative body.",
            "4. Charter Act of 1853:",
            "   • Separated executive and legislative functions of GG's Council for the first time.",
            "   • Added 6 new legislative councillors (Indian Central Legislative Council).",
            "   • Introduced open competition for civil services selection (Macaulay Committee appointed in 1854).",
            "",
            "B. THE CROWN RULE (1858–1947)",
            "1. Government of India Act 1858 ('Act for the Good Government of India'):",
            "   • Abolished EIC; transferred government, territories, and revenues to British Crown.",
            "   • Changed designation of GG of India to 'Viceroy of India' (Lord Canning was first).",
            "   • Ended system of Double Government by abolishing Board of Control and Court of Directors.",
            "   • Created office of 'Secretary of State for India' (Cabinet Minister in UK) assisted by 15-member Council of India.",
            "2. Indian Councils Act 1909 (Morley-Minto Reforms):",
            "   • Increased size of Central & Provincial Legislative Councils.",
            "   • Introduced system of SEPARATE ELECTORATE for Muslims (Lord Minto known as 'Father of Communal Electorate').",
            "   • Allowed Indians in Executive Councils: Satyendra Prasad Sinha was first Indian to join Viceroy's Executive Council (as Law Member).",
            "3. Government of India Act 1919 (Montagu-Chelmsford Reforms):",
            "   • Introduced DYARCHY in Provinces: Transferred Subjects (Governor + Ministers) and Reserved Subjects (Governor + Executive Council).",
            "   • Introduced BICAMERALISM and DIRECT ELECTIONS at Centre.",
            "   • Created Public Service Commission (established in 1926).",
            "4. Government of India Act 1935:",
            "   • Envisaged All-India Federation comprising Provinces and Princely States (never materialized).",
            "   • Divided powers into Federal List (59), Provincial List (54), Concurrent List (36); Residuary powers with Viceroy.",
            "   • Abolished Dyarchy in Provinces; introduced 'Provincial Autonomy'. Introduced Dyarchy at Centre.",
            "   • Established Federal Court (1937) and Reserve Bank of India (1935).",
            "",
            "==================================================",
            "PART 2: MAINS ANALYTICAL INSIGHTS & CASE STUDY",
            "==================================================",
            "• Constitutional Continuity: Over 60% of the text and structural framework of the Constitution of India (1950) is directly adapted from the Government of India Act 1935 (Federal structure, Emergency powers, Administrative details, Judiciary).",
            "• Legacy of Communal Electorates: The 1909 Act introduced institutionalized communalism which escalated through the 1919 and 1932 Communal Award, eventually culminating in the Partition of 1947.",
            "• Evolution of Separation of Powers: Charter Act of 1853 laid the mini-parliamentary seed by creating the legislative council, distinguishing law-making from executive execution."
          ),
          flashcards = listOf(
            Flashcard(
              id = "f_pol1",
              question = "Who was the first Governor-General of Bengal?",
              answer = "Lord Warren Hastings under the Regulating Act of 1773.",
              category = "Constitutional History"
            ),
            Flashcard(
              id = "f_pol2",
              question = "Which Act introduced separate electorates for Muslims?",
              answer = "Indian Councils Act 1909 (Morley-Minto Reforms). Lord Minto is called Father of Communal Electorate.",
              category = "Electoral System"
            ),
            Flashcard(
              id = "f_pol3",
              question = "Which Act created the system of 'Double Government'?",
              answer = "Pitt's India Act of 1784 (Court of Directors for commercial & Board of Control for political affairs).",
              category = "Colonial Acts"
            ),
            Flashcard(
              id = "f_pol4_new",
              question = "Who was the first Indian to join the Viceroy's Executive Council?",
              answer = "Satyendra Prasad Sinha (joined as the Law Member under Indian Councils Act 1909).",
              category = "Executive Appointments"
            ),
            Flashcard(
              id = "f_pol5_new",
              question = "Which Act introduced 'Dyarchy' in the Provinces?",
              answer = "Government of India Act 1919 (Montagu-Chelmsford Reforms), separating Transferred and Reserved subjects.",
              category = "Provincial Governance"
            )
          ),
          quizQuestions = listOf(
            QuizQuestion(
              id = "q_pol1",
              questionText = "Consider the following statements regarding the Charter Act of 1833:\n1. It made the Governor-General of Bengal as Governor-General of India.\n2. It ended the commercial activities of the East India Company completely.\n3. It introduced an open competition system for civil service selection for the first time.\nWhich of the statements given above is/are correct?",
              options = listOf("A. 1 and 2 only", "B. 2 and 3 only", "C. 1 and 3 only", "D. 1, 2 and 3"),
              correctAnswerIndex = 0,
              explanation = "Statements 1 and 2 are correct. Statement 3 is incorrect because civil service open competition was only attempted in 1833 but negated due to opposition from Court of Directors; it was introduced in Charter Act 1853."
            ),
            QuizQuestion(
              id = "q_pol2",
              questionText = "Which Act envisaged an All-India Federation comprising Provinces and Princely States as units?",
              options = listOf("A. Government of India Act 1919", "B. Government of India Act 1935", "C. Indian Independence Act 1947", "D. Indian Councils Act 1892"),
              correctAnswerIndex = 1,
              explanation = "The Government of India Act 1935 provided for an All-India Federation, though it never came into operation as Princely States did not join."
            ),
            QuizQuestion(
              id = "q_pol3_new",
              questionText = "With reference to the Indian Councils Act 1909, consider the following statements:\n1. It introduced a system of communal representation for Muslims.\n2. It gave members the right to ask supplementary questions and move resolutions on the budget.\nWhich of the statements given above is/are correct?",
              options = listOf("A. 1 only", "B. 2 only", "C. Both 1 and 2", "D. Neither 1 nor 2"),
              correctAnswerIndex = 2,
              explanation = "Both statements are correct. Morley-Minto reforms introduced separate electorates for Muslims and expanded legislative powers including supplementary questions."
            ),
            QuizQuestion(
              id = "q_pol4_new",
              questionText = "Under the Government of India Act 1919, which of the following were treated as 'Transferred Subjects' in Provinces?\n1. Local Self-Government\n2. Health and Education\n3. Police and Revenue\nSelect the correct answer using the code below:",
              options = listOf("A. 1 and 2 only", "B. 2 and 3 only", "C. 1 and 3 only", "D. 1, 2 and 3"),
              correctAnswerIndex = 0,
              explanation = "Local self-government, health, education, and agriculture were Transferred subjects administered by Governor with ministers. Police, land revenue, and finance were Reserved subjects."
            ),
            QuizQuestion(
              id = "q_pol5_new",
              questionText = "Which of the following Acts first used the expression 'British Possessions in India'?",
              options = listOf("A. Regulating Act 1773", "B. Pitt's India Act 1784", "C. Charter Act 1813", "D. Charter Act 1833"),
              correctAnswerIndex = 1,
              explanation = "Pitt's India Act of 1784 officially designated Company's territories in India as 'British possessions in India'."
            )
          )
        ),
        Chapter(
          id = "pol_ch2",
          title = "Ch 2: Making of the Constitution",
          subtitle = "Constituent Assembly Committees & Drafting Process",
          isPremium = false,
          textContent = listOf(
            "The Constituent Assembly was constituted in November 1946 under the scheme formulated by the Cabinet Mission Plan.",
            "Composition & Working:",
            "• Total strength was 389 (296 British India, 93 Princely States).",
            "• First meeting on December 9, 1946 (Dr. Sachchidananda Sinha elected temporary President). Dr. Rajendra Prasad elected permanent President on Dec 11.",
            "• Objective Resolution moved by Jawaharlal Nehru on Dec 13, 1946 (adopted unanimously on Jan 22, 1947). Formed the basis of Preamble.",
            "Key Committees:",
            "1. Drafting Committee: Chaired by Dr. B.R. Ambedkar (7 members).",
            "2. Union Powers Committee: Jawaharlal Nehru.",
            "3. Steering Committee & Rules of Procedure: Dr. Rajendra Prasad.",
            "4. Provincial Constitution Committee: Sardar Vallabhbhai Patel.",
            "5. Fundamental Rights & Minorities Advisory Committee: Sardar Patel."
          ),
          flashcards = listOf(
            Flashcard(
              id = "f_pol4",
              question = "Who moved the historic 'Objectives Resolution' in the Constituent Assembly?",
              answer = "Pandit Jawaharlal Nehru on December 13, 1946.",
              category = "Constituent Assembly"
            ),
            Flashcard(
              id = "f_pol5",
              question = "Who was the Constitutional Advisor to the Constituent Assembly?",
              answer = "Sir B.N. Rau.",
              category = "Constitutional History"
            )
          ),
          quizQuestions = listOf(
            QuizQuestion(
              id = "q_pol3",
              questionText = "Who was the Chairman of the Drafting Committee of the Constituent Assembly?",
              options = listOf("A. Dr. Rajendra Prasad", "B. Dr. B.R. Ambedkar", "C. Jawaharlal Nehru", "D. Sardar Patel"),
              correctAnswerIndex = 1,
              explanation = "Dr. B.R. Ambedkar was the Chairman of the Drafting Committee, set up on August 29, 1947."
            )
          )
        ),
        Chapter(
          id = "pol_ch3",
          title = "Ch 3: Salient Features & Preamble",
          subtitle = "Lengthiest Written Constitution, Borrowed Features & Philosophy",
          isPremium = true,
          textContent = listOf(
            "The Indian Constitution is unique in its contents and spirit, incorporating features from various national constitutions.",
            "Major Borrowed Features:",
            "• British Constitution: Parliamentary government, Rule of Law, Legislative procedure, Single Citizenship, Cabinet system, Prerogative writs.",
            "• US Constitution: Fundamental Rights, Judicial Independence, Judicial Review, Impeachment of President, Removal of SC/HC judges.",
            "• Irish Constitution: Directive Principles of State Policy (DPSP), Nomination of members to Rajya Sabha, Method of Presidential election.",
            "• Canadian Constitution: Federation with strong Centre, Residuary powers with Centre, Appointment of Governors by Centre.",
            "• Australian Constitution: Concurrent List, Freedom of trade/commerce, Joint sitting of two houses of Parliament."
          ),
          flashcards = listOf(
            Flashcard(
              id = "f_pol6",
              question = "From which constitution were the Directive Principles of State Policy (DPSP) borrowed?",
              answer = "Irish Constitution (which borrowed from Spanish Constitution).",
              category = "Sources of Constitution"
            )
          ),
          quizQuestions = listOf(
            QuizQuestion(
              id = "q_pol4",
              questionText = "The concept of 'Judicial Review' in the Indian Constitution is inspired by which country's constitution?",
              options = listOf("A. United Kingdom", "B. United States of America", "C. Canada", "D. Australia"),
              correctAnswerIndex = 1,
              explanation = "Judicial Review and Independence of Judiciary are borrowed from the US Constitution."
            )
          )
        ),
        Chapter(
          id = "pol_ch4",
          title = "Ch 4: Fundamental Rights (Art 12–35)",
          subtitle = "Right to Equality, Freedom, Religion & Constitutional Remedies",
          isPremium = true,
          textContent = listOf(
            "Part III of the Constitution (Articles 12 to 35) guarantees six Fundamental Rights to all citizens.",
            "Categories:",
            "1. Right to Equality (Art 14–18): Equality before law, Prohibition of discrimination, Equality of opportunity, Abolition of Untouchability & Titles.",
            "2. Right to Freedom (Art 19–22): Six freedoms under Art 19, Protection in respect of conviction (Art 20), Protection of life and personal liberty (Art 21), Right to Education (Art 21A), Protection against arrest (Art 22).",
            "3. Right against Exploitation (Art 23–24): Prohibition of human trafficking & forced labor, Prohibition of child labor in hazardous employment.",
            "4. Right to Freedom of Religion (Art 25–28): Freedom of conscience, managing religious affairs, payment of taxes for religion.",
            "5. Cultural & Educational Rights (Art 29–30): Protection of minority interests and right to establish educational institutions.",
            "6. Right to Constitutional Remedies (Art 32): Writs of Habeas Corpus, Mandamus, Prohibition, Quo-Warranto, Certiorari."
          ),
          flashcards = listOf(
            Flashcard(
              id = "f_pol7",
              question = "Which writ prevents illegal detention of a person?",
              answer = "Habeas Corpus (literally 'To have the body of').",
              category = "Writs"
            )
          ),
          quizQuestions = listOf(
            QuizQuestion(
              id = "q_pol5",
              questionText = "Which Article cannot be suspended even during a National Emergency declared under Article 352?",
              options = listOf("A. Articles 14 and 19", "B. Articles 20 and 21", "C. Articles 21 and 22", "D. Articles 19 and 20"),
              correctAnswerIndex = 1,
              explanation = "Articles 20 and 21 remain enforceable even during a National Emergency."
            )
          )
        ),
        Chapter(
          id = "pol_ch5",
          title = "Ch 5: DPSP & Fundamental Duties",
          subtitle = "Part IV (Art 36–51) & Part IVA (Art 51A)",
          isPremium = true,
          textContent = listOf(
            "Directive Principles of State Policy (DPSP) are fundamental in governance, aiming to establish a Welfare State.",
            "DPSP Categorization:",
            "• Socialistic Principles: Equal pay for equal work (Art 39), Right to work & education (Art 41), Living wage for workers (Art 43).",
            "• Gandhian Principles: Organize Village Panchayats (Art 40), Promote cottage industries (Art 43), Promote SC/ST educational interests (Art 46), Prohibition of intoxicating drinks (Art 47).",
            "• Liberal-Intellectual Principles: Uniform Civil Code (Art 44), Early childhood care (Art 45), Separation of Judiciary from Executive (Art 50), International peace (Art 51).",
            "Fundamental Duties (Part IVA, Art 51A):",
            "• Added by 42nd Amendment 1976 on recommendation of Swaran Singh Committee (10 duties). 11th duty added by 86th Amendment 2002."
          ),
          flashcards = listOf(
            Flashcard(
              id = "f_pol8",
              question = "Which Committee recommended the inclusion of Fundamental Duties?",
              answer = "Swaran Singh Committee (1976).",
              category = "Fundamental Duties"
            )
          ),
          quizQuestions = listOf(
            QuizQuestion(
              id = "q_pol6",
              questionText = "Uniform Civil Code is mentioned under which Article of DPSP?",
              options = listOf("A. Article 40", "B. Article 44", "C. Article 48", "D. Article 50"),
              correctAnswerIndex = 1,
              explanation = "Article 44 mandates that the State shall endeavor to secure for citizens a Uniform Civil Code throughout India."
            )
          )
        ),
        Chapter(
          id = "pol_ch6",
          title = "Ch 6: Parliament & Executive",
          subtitle = "President, Prime Minister, Lok Sabha & Rajya Sabha",
          isPremium = true,
          textContent = listOf(
            "India follows a Westminister Parliamentary system with an Executive responsible to the Legislature.",
            "President of India (Art 52–62):",
            "• Executive head of State, elected by Electoral College consisting of elected members of both houses of Parliament and legislative assemblies.",
            "• Ordinance making power (Art 123), Pardoning power (Art 72).",
            "Parliament (Lok Sabha & Rajya Sabha):",
            "• Money Bills (Art 110) can only be introduced in Lok Sabha with prior recommendation of President.",
            "• Rajya Sabha has exclusive powers: Art 249 (Authorize Parliament to legislate on State List) and Art 312 (Creation of All-India Services)."
          ),
          flashcards = listOf(
            Flashcard(
              id = "f_pol9",
              question = "Which Article empowers the President to issue Ordinances during parliamentary recess?",
              answer = "Article 123.",
              category = "Executive Powers"
            )
          ),
          quizQuestions = listOf(
            QuizQuestion(
              id = "q_pol7",
              questionText = "Which Article gives Rajya Sabha the exclusive power to recommend the creation of new All-India Services?",
              options = listOf("A. Article 249", "B. Article 312", "C. Article 368", "D. Article 110"),
              correctAnswerIndex = 1,
              explanation = "Article 312 empowers Rajya Sabha to pass a resolution by 2/3rd majority to create an All-India Service."
            )
          )
        )
      )
    ),

    // ==========================================
    // 2. MODERN INDIAN HISTORY (Spectrum)
    // ==========================================
    Subject(
      id = "history",
      name = "Modern History",
      chapters = listOf(
        Chapter(
          id = "hist_ch1",
          title = "Ch 1: Sources & Advent of Europeans",
          subtitle = "Portuguese, Dutch, English & French Rivalry (Spectrum Unit 1)",
          isPremium = false,
          textContent = listOf(
            "==================================================",
            "PART 1: HISTORICAL TIMELINE & EUROPEAN ARRIVAL",
            "==================================================",
            "The fall of Constantinople (1453) to Ottoman Turks closed land routes to Asia, compelling European nations to seek direct sea routes to India.",
            "A. CHRONOLOGY OF EUROPEAN POWERS",
            "1. THE PORTUGUESE (1498–1961):",
            "   • Vasco da Gama reached Calicut on May 20, 1498 (welcomed by Zamorin / Manavikrama).",
            "   • Pedro Alvarez Cabral established first factory at Calicut (1500).",
            "   • Francisco de Almeida (1505) initiated 'Blue Water Policy' (Cartaz System - maritime trade passes).",
            "   • Afonso de Albuquerque (1509–1515): Real founder of Portuguese power in India. Captured Goa from Sultan of Bijapur (1510). Abolished Sati in Goa.",
            "   • Nino da Cunha (1529) shifted Portuguese capital from Cochin to Goa (1530).",
            "   • Decline: Religious intolerance, Inquisition, rise of Dutch/English, discovery of Brazil.",
            "",
            "2. THE DUTCH (1602–1759):",
            "   • Vereenigde Oostindische Compagnie (VOC) formed in 1602.",
            "   • First factory at Masulipatnam (1605), Pulicat (1610 - minted Gold Pagodas), Surat (1616), Chinsurah (1653).",
            "   • Focused heavily on spice islands of Indonesia rather than India.",
            "   • Defeated decisively by British in Battle of Bedara/Hooghly (1759).",
            "",
            "3. THE ENGLISH EAST INDIA COMPANY (1600–1858):",
            "   • Royal Charter granted by Queen Elizabeth I on Dec 31, 1600 ('Governor and Company of Merchants of London trading into the East Indies').",
            "   • Captain William Hawkins visited Jahangir's court (1608); Sir Thomas Roe obtained imperial farmans (1615).",
            "   • First factory in South at Masulipatnam (1611); First factory in West at Surat (1612) after Captain Best defeated Portuguese in Battle of Swally (1612).",
            "   • Golden Farman from Sultan of Golconda (1632); Fort St. George built at Madras (1639).",
            "   • Job Charnock founded Calcutta (1690) by combining Sutanuti, Gobindapur, and Kalikata; Fort William built (1700).",
            "   • Farrukhsiyar's Farman (1717): Magna Carta of EIC - Duty-free trade in Bengal for annual payment of ₹3,000.",
            "",
            "4. THE FRENCH (1664–1760):",
            "   • Compagnie des Indes Orientales created by Colbert under King Louis XIV (1664).",
            "   • First factory at Surat (1668) by François Caron; Pondicherry founded in 1674 by François Martin.",
            "   • Joseph François Dupleix introduced Subsidiary Alliance model and native troops intervention.",
            "   • Three Carnatic Wars (1746–1763): Battle of St. Thome (1746), Battle of Ambur (1749), Battle of Wandiwash (1760 - Eyre Coote defeated Lally). Ended French political sway (Treaty of Paris 1763).",
            "",
            "==================================================",
            "PART 2: MAINS ANALYTICAL INSIGHTS",
            "==================================================",
            "• Why the English Succeeded over other European Rivals:",
            "  1. Structure & Control: EIC was a private joint-stock corporation (flexible, quick decisions) unlike state-controlled French company (bureaucracy, funding delays).",
            "  2. Naval & Technological Superiority: Industrial revolution gave British superior ships, iron cannons, and disciplined army.",
            "  3. Financial Stability: British banking system & London money market financed prolonged wars.",
            "  4. Strategic Imperial Focus: British prioritized Bengal (wealthy, fertile) over spice islands, utilizing Bengal's revenues to conquer rest of India."
          ),
          flashcards = listOf(
            Flashcard(
              id = "f_hist1",
              question = "Who instituted the 'Blue Water Policy' in Portuguese India?",
              answer = "Francisco de Almeida (first Portuguese Viceroy in India).",
              category = "European Expansion"
            ),
            Flashcard(
              id = "f_hist2",
              question = "Which battle decisively ended French political ambitions in India?",
              answer = "Battle of Wandiwash (1760) where General Eyre Coote defeated Comte de Lally.",
              category = "Carnatic Wars"
            ),
            Flashcard(
              id = "f_hist3_new",
              question = "Which Mughal emperor issued the 'Farrukhsiyar Farman' of 1717 to the EIC?",
              answer = "Farrukhsiyar (granted duty-free trade in Bengal for ₹3,000/year, called Magna Carta of EIC).",
              category = "Mughal Farmans"
            ),
            Flashcard(
              id = "f_hist4_new",
              question = "Who captured Goa from the Sultan of Bijapur in 1510?",
              answer = "Afonso de Albuquerque (considered the real founder of Portuguese power in India).",
              category = "Portuguese Conquest"
            ),
            Flashcard(
              id = "f_hist5_new",
              question = "Which battle ended Dutch naval power in India in 1759?",
              answer = "Battle of Bedara (or Battle of Hooghly), fought between British and Dutch forces.",
              category = "European Battles"
            )
          ),
          quizQuestions = listOf(
            QuizQuestion(
              id = "q_hist1",
              questionText = "Consider the following European powers in India:\n1. Dutch\n2. English\n3. French\n4. Portuguese\nWhich is the correct chronological order of their arrival in India?",
              options = listOf("A. 4 - 1 - 2 - 3", "B. 4 - 2 - 1 - 3", "C. 1 - 4 - 2 - 3", "D. 4 - 1 - 3 - 2"),
              correctAnswerIndex = 0,
              explanation = "Chronology: Portuguese (1498) -> Dutch (1602) -> English (1600 charter, 1608 arrival) -> French (1664)."
            ),
            QuizQuestion(
              id = "q_hist2_new",
              questionText = "With reference to Portuguese rule in India, consider the following statements:\n1. Afonso de Albuquerque abolished the practice of Sati in Goa.\n2. Francisco de Almeida instituted the Cartaz System to regulate Indian Ocean trade.\n3. Goa was made the capital of Portuguese settlements by Nino da Cunha.\nWhich of the statements given above are correct?",
              options = listOf("A. 1 and 2 only", "B. 2 and 3 only", "C. 1 and 3 only", "D. 1, 2 and 3"),
              correctAnswerIndex = 3,
              explanation = "All three statements are correct. Albuquerque abolished Sati in Goa; Almeida initiated Blue Water Policy & Cartaz; Nino da Cunha shifted capital from Cochin to Goa in 1530."
            ),
            QuizQuestion(
              id = "q_hist3_new",
              questionText = "Which one of the following was the immediate cause of the First Carnatic War (1746–1748)?",
              options = listOf("A. Maratha invasion of Carnatic", "B. Austrian War of Succession in Europe", "C. Capture of French ships by British Navy under Commodore Curtis Barnett", "D. Rivalry between Chanda Sahib and Muzaffar Jang"),
              correctAnswerIndex = 2,
              explanation = "Though triggered by the War of Austrian Succession in Europe, the immediate cause in India was the seizure of French ships by the English Navy under Barnett."
            ),
            QuizQuestion(
              id = "q_hist4_new",
              questionText = "Which Mughal Emperor granted the 'Golden Farman' to the English East India Company in 1632?",
              options = listOf("A. Jahangir", "B. Sultan of Golconda", "C. Shah Jahan", "D. Aurangzeb"),
              correctAnswerIndex = 1,
              explanation = "The Sultan of Golconda issued the Golden Farman in 1632, permitting the English to trade freely in the ports of Golconda for 500 Pagodas a year."
            ),
            QuizQuestion(
              id = "q_hist5_new",
              questionText = "Consider the following statements regarding the French East India Company:\n1. It was a private joint-stock enterprise like the English East India Company.\n2. François Martin founded Pondicherry in 1674.\nWhich of the statements given above is/are correct?",
              options = listOf("A. 1 only", "B. 2 only", "C. Both 1 and 2", "D. Neither 1 nor 2"),
              correctAnswerIndex = 1,
              explanation = "Statement 1 is incorrect because the French company was state-created and state-financed by Colbert/Louis XIV, making it dependent on government funds. Statement 2 is correct."
            )
          )
        ),
        Chapter(
          id = "hist_ch2",
          title = "Ch 2: Revolt of 1857 & Early Resistance",
          subtitle = "Causes, Key Centers, Leaders & Suppression",
          isPremium = false,
          textContent = listOf(
            "The Revolt of 1857 was the first major armed uprising against British East India Company rule.",
            "Causes:",
            "• Political: Subsidiary Alliance (Lord Wellesley), Doctrine of Lapse (Lord Dalhousie - annexed Satara, Sambalpur, Jhansi, Nagpur, Awadh).",
            "• Economic: Heavy land revenue settlements (Zamindari, Ryotwari, Mahalwari), destruction of traditional handicrafts.",
            "• Socio-Religious: Abolition of Sati (1829), Widow Remarriage Act (1856), fears of Christian conversions.",
            "• Immediate Cause: Introduction of Enfield Rifle with cartridges greased with cow & pig fat (Mangal Pandey at Barrackpore).",
            "Key Centers & Leaders:",
            "• Delhi: Bahadur Shah Zafar & General Bakht Khan.",
            "• Kanpur: Nana Sahib & Tantia Tope.",
            "• Lucknow: Begum Hazrat Mahal.",
            "• Jhansi: Rani Lakshmibai.",
            "• Bihar (Jagdishpur): Kunwar Singh."
          ),
          flashcards = listOf(
            Flashcard(
              id = "f_hist3",
              question = "Who led the 1857 revolt in Jagdishpur, Bihar?",
              answer = "Kunwar Singh (and his brother Amar Singh).",
              category = "1857 Revolt"
            )
          ),
          quizQuestions = listOf(
            QuizQuestion(
              id = "q_hist2",
              questionText = "Which British policy was responsible for the annexation of Satara, Jhansi, and Nagpur prior to 1857?",
              options = listOf("A. Subsidiary Alliance", "B. Doctrine of Lapse", "C. Permanent Settlement", "D. Ring Fence Policy"),
              correctAnswerIndex = 1,
              explanation = "Lord Dalhousie applied the Doctrine of Lapse to annex states without direct male heirs."
            )
          )
        ),
        Chapter(
          id = "hist_ch3",
          title = "Ch 3: Socio-Religious Reform Movements",
          subtitle = "Brahmo Samaj, Arya Samaj, Ramakrishna Mission & Aligarh Movement",
          isPremium = true,
          textContent = listOf(
            "19th century India witnessed a renaissance led by reform movements aimed at eradicating social evils and promoting rationalism.",
            "Major Reform Movements:",
            "1. Brahmo Samaj (1828): Founded by Raja Ram Mohan Roy ('Father of Modern India'). Opposed Sati, idol worship, caste rigidities. Sati abolished in 1829 by William Bentinck.",
            "2. Young Bengal Movement (1820s): Henry Vivian Derozio. Inspired radical free thought among youth.",
            "3. Arya Samaj (1875): Founded by Swami Dayananda Saraswati in Bombay. Slogan: 'Go back to Vedas'. Authored 'Satyarth Prakash'.",
            "4. Ramakrishna Mission (1897): Founded by Swami Vivekananda to propagate humanitarian service and Vedanta teachings.",
            "5. Satya Shodhak Samaj (1873): Jyotirao Phule in Maharashtra. Fought against caste oppression; authored 'Gulamgiri'.",
            "6. Aligarh Movement: Sir Syed Ahmed Khan. Founded Mohammedan Anglo-Oriental College (1875) at Aligarh."
          ),
          flashcards = listOf(
            Flashcard(
              id = "f_hist4",
              question = "Who authored the anti-caste book 'Gulamgiri' and founded Satya Shodhak Samaj?",
              answer = "Jyotirao Phule (1873).",
              category = "Social Reforms"
            )
          ),
          quizQuestions = listOf(
            QuizQuestion(
              id = "q_hist3",
              questionText = "The 'Young Bengal Movement' was started by:",
              options = listOf("A. Raja Ram Mohan Roy", "B. Henry Vivian Derozio", "C. Debendranath Tagore", "D. Ishwar Chandra Vidyasagar"),
              correctAnswerIndex = 1,
              explanation = "Henry Vivian Derozio, a teacher at Hindu College, Kolkata, led the radical Young Bengal Movement."
            )
          )
        ),
        Chapter(
          id = "hist_ch4",
          title = "Ch 4: INC Formation & Freedom Struggle",
          subtitle = "Moderates, Extremists, Swadeshi & Gandhian Movements",
          isPremium = true,
          textContent = listOf(
            "The Indian National Congress (INC) was formed in December 1885 at Bombay (Gokuldas Tejpal Sanskrit College) by A.O. Hume.",
            "Phases of Freedom Struggle:",
            "1. Moderate Phase (1885–1905): Dadabhai Naoroji, W.C. Bonnerjee, Gopal Krishna Gokhale. Used petitions, prayers, and economic critique (Drain of Wealth theory).",
            "2. Extremist Phase (1905–1919): Tilak, Lajpat Rai, Bipin Chandra Pal (Lal-Bal-Pal), Aurobindo Ghosh. Swadeshi and Boycott Movement against Partition of Bengal (1905). Surat Split in 1907.",
            "3. Gandhian Phase (1919–1947):",
            "• Early Experiments: Champaran (1917), Kheda (1918), Ahmedabad Mill Strike (1918).",
            "• Non-Cooperation Movement (1920–22): Launched after Rowlatt Act & Jallianwala Bagh Massacre. Suspended after Chauri Chaura incident.",
            "• Civil Disobedience Movement (1930): Commenced with Dandi Salt March (6 March – 5 April 1930).",
            "• Quit India Movement (1942): Launched on August 8, 1942 ('Do or Die' slogan)."
          ),
          flashcards = listOf(
            Flashcard(
              id = "f_hist5",
              question = "Which incident led Gandhi to suspend the Non-Cooperation Movement in 1922?",
              answer = "Chauri Chaura incident in Gorakhpur, UP (february 1922) where a police station was burned.",
              category = "Gandhian Era"
            )
          ),
          quizQuestions = listOf(
            QuizQuestion(
              id = "q_hist4",
              questionText = "Which movement began with the historic Dandi March led by Mahatma Gandhi?",
              options = listOf("A. Non-Cooperation Movement", "B. Civil Disobedience Movement", "C. Quit India Movement", "D. Swadeshi Movement"),
              correctAnswerIndex = 1,
              explanation = "The Dandi March from Sabarmati Ashram to Dandi coast inaugurated the Civil Disobedience Movement in April 1930."
            )
          )
        )
      )
    ),

    // ==========================================
    // 3. GEOGRAPHY (Class 11 & 12 NCERTs)
    // ==========================================
    Subject(
      id = "geography",
      name = "Geography",
      chapters = listOf(
        Chapter(
          id = "geo_ch1",
          title = "Ch 1: Physical Geography & Earth's Interior",
          subtitle = "Crust, Mantle, Core, Seismic Waves & Plate Tectonics",
          isPremium = false,
          textContent = listOf(
            "Understanding Earth's interior relies on direct (rocks, mining, volcanic eruption) and indirect sources (density, temperature, pressure, seismic waves).",
            "Earth Structure Layers:",
            "1. Crust: Outermost solid layer. Oceanic crust (thinner, denser - Sima) and Continental crust (thicker, less dense - Sial).",
            "2. Mantle: Extends to 2900 km depth. Upper mantle contains 'Asthenosphere' (weak, plastic layer on which tectonic plates float).",
            "3. Core: Outer core (liquid) and Inner core (solid, NiFe - Nickel & Iron).",
            "Seismic Waves:",
            "• P-Waves (Primary): Longitudinal/Compressional, fastest, pass through solids, liquids, and gases.",
            "• S-Waves (Secondary): Transverse/Shear, slower, pass ONLY through solids (creates S-wave shadow zone beyond 103°).",
            "Plate Tectonics:",
            "• Proposed by Morgan, Mackenzie, and Parker (1967) building on Wegener's Continental Drift Theory. Major plates: Pacific, North American, South American, Eurasian, African, Indo-Australian, Antarctic."
          ),
          flashcards = listOf(
            Flashcard(
              id = "f_geo1",
              question = "Which seismic waves can travel ONLY through solid media?",
              answer = "S-Waves (Secondary / Transverse Waves).",
              category = "Seismology"
            ),
            Flashcard(
              id = "f_geo2",
              question = "What is the weak plastic layer in the upper mantle called?",
              answer = "Asthenosphere.",
              category = "Earth Interior"
            )
          ),
          quizQuestions = listOf(
            QuizQuestion(
              id = "q_geo1",
              questionText = "Consider the following statements regarding P-waves and S-waves:\n1. P-waves travel faster than S-waves.\n2. S-waves can travel through both solid and liquid layers.\nWhich of the statements given above is/are correct?",
              options = listOf("A. 1 only", "B. 2 only", "C. Both 1 and 2", "D. Neither 1 nor 2"),
              correctAnswerIndex = 0,
              explanation = "Statement 1 is correct (P-waves are fastest). Statement 2 is incorrect because S-waves cannot pass through liquid media."
            )
          )
        ),
        Chapter(
          id = "geo_ch2",
          title = "Ch 2: Indian Physiography & Drainage",
          subtitle = "Himalayas, Northern Plains, Peninsular Plateau & Drainage Systems",
          isPremium = false,
          textContent = listOf(
            "India exhibits diverse physical features divided into six major physiographic divisions.",
            "Physiographic Divisions:",
            "1. Northern Mountains (Himalayas): Young fold mountains. Parallel ranges: Trans-Himalayas, Greater Himalayas (Himadri), Lesser Himalayas (Himachal), Outer Himalayas (Shiwaliks).",
            "2. Northern Plains: Formed by alluvial deposition of Indus, Ganga, and Brahmaputra. Zones: Bhabar (pebbles), Terai (marshy), Bhangar (old alluvium), Khadar (new fertile alluvium).",
            "3. Peninsular Plateau: Oldest landmass (Gondwanaland part). Includes Deccan Plateau, Central Highlands, Western Ghats (Sahyadris - continuous) and Eastern Ghats (discontinuous).",
            "Drainage Systems:",
            "• Himalayan Rivers: Antecedent, perennial, high erosion (Ganga, Indus, Brahmaputra).",
            "• Peninsular Rivers: Superimposed, seasonal, mature valleys. East flowing (Godavari, Krishna, Cauvery - forms deltas) vs West flowing (Narmada, Tapti - flows in rift valleys, forms estuaries)."
          ),
          flashcards = listOf(
            Flashcard(
              id = "f_geo3",
              question = "Which plain belt is characterized by coarse pebbles where streams disappear underground?",
              answer = "Bhabar belt along the foothills of Shiwaliks.",
              category = "Indian Relief"
            ),
            Flashcard(
              id = "f_geo4",
              question = "Which major West-flowing peninsular rivers flow through rift valleys?",
              answer = "Narmada and Tapti (Mahi & Sabarmati also).",
              category = "Indian Rivers"
            )
          ),
          quizQuestions = listOf(
            QuizQuestion(
              id = "q_geo2",
              questionText = "Which river is known as 'Dakshin Ganga' and is the longest river system of Peninsular India?",
              options = listOf("A. Krishna", "B. Godavari", "C. Cauvery", "D. Mahanadi"),
              correctAnswerIndex = 1,
              explanation = "Godavari (1465 km) is the largest peninsular river, often termed Dakshin Ganga or Vriddha Ganga."
            )
          )
        ),
        Chapter(
          id = "geo_ch3",
          title = "Ch 3: Atmosphere, Monsoons & Climate",
          subtitle = "Structure of Atmosphere, Insolation, Indian Monsoons & El Niño",
          isPremium = true,
          textContent = listOf(
            "The Indian monsoon is a complex atmospheric phenomenon driven by differential heating of land and sea.",
            "Atmosphere Layers:",
            "• Troposphere: Lowest layer (all weather phenomena occur here).",
            "• Stratosphere: Contains Ozone layer (absorbs UV radiation); ideal for jet flying.",
            "• Mesosphere: Coldest layer; meteors burn up here.",
            "• Thermosphere / Ionosphere: Contains charged ions; reflects radio waves.",
            "Monsoon Mechanism & Factors:",
            "1. Thermal Contrast: Heating of Tibetan Plateau during summer creates low pressure.",
            "2. ITCZ Shift: Shift of Inter-Tropical Convergence Zone over Ganga Plain in summer.",
            "3. Tropical Easterly Jet Stream (TEJ) & Somali Jet.",
            "4. El Niño / Southern Oscillation (ENSO): Warming of eastern Pacific generally suppresses Indian monsoon, while Positive Indian Ocean Dipole (IOD) enhances rainfall."
          ),
          flashcards = listOf(
            Flashcard(
              id = "f_geo5",
              question = "Which atmospheric layer contains the Ozone layer?",
              answer = "Stratosphere.",
              category = "Climatology"
            )
          ),
          quizQuestions = listOf(
            QuizQuestion(
              id = "q_geo3",
              questionText = "A 'Positive Indian Ocean Dipole (IOD)' is generally associated with:",
              options = listOf("A. Drought in India", "B. Good/Enhanced Monsoon rainfall in India", "C. Cyclones in Atlantic Ocean", "D. Severe cold waves in South India"),
              correctAnswerIndex = 1,
              explanation = "A positive IOD brings warmer sea surface temperatures in western Indian Ocean, favoring strong Indian monsoons."
            )
          )
        )
      )
    ),

    // ==========================================
    // 4. INDIAN ECONOMY (Ramesh Singh / Standard)
    // ==========================================
    Subject(
      id = "economy",
      name = "Indian Economy",
      chapters = listOf(
        Chapter(
          id = "econ_ch1",
          title = "Ch 1: National Income Accounting",
          subtitle = "GDP, GNP, NNP, Factor Cost & Real Growth",
          isPremium = false,
          textContent = listOf(
            "National Income measures the net value of goods and services produced in a country in a financial year.",
            "Core Aggregate Metrics:",
            "1. Gross Domestic Product (GDP): Total market value of final goods & services produced inside territorial boundaries.",
            "2. Gross National Product (GNP): GDP + Net Factor Income from Abroad (NFIA). Accounts for domestic citizens earning abroad minus foreign residents earning in India.",
            "3. Net Domestic Product (NDP): GDP minus Depreciation.",
            "4. Real vs Nominal GDP: Nominal GDP uses current market prices; Real GDP uses constant base year prices to remove inflation distortion.",
            "5. GDP Deflator: Ratio of Nominal GDP to Real GDP multiplied by 100. Broadest measure of economy-wide inflation."
          ),
          flashcards = listOf(
            Flashcard(
              id = "f_econ1",
              question = "Formula for Gross National Product (GNP)?",
              answer = "GNP = GDP + Net Factor Income from Abroad (NFIA).",
              category = "Macroeconomics"
            ),
            Flashcard(
              id = "f_econ2",
              question = "What is the official definition of National Income in India?",
              answer = "Net National Product at Factor Cost (NNP at FC).",
              category = "National Income"
            )
          ),
          quizQuestions = listOf(
            QuizQuestion(
              id = "q_econ1",
              questionText = "Gross Domestic Product at Market Price (GDP at MP) minus Net Indirect Taxes equals:",
              options = listOf("A. GDP at Factor Cost", "B. NNP at Factor Cost", "C. Personal Income", "D. Gross National Product"),
              correctAnswerIndex = 0,
              explanation = "Factor Cost = Market Price - Net Indirect Taxes (Indirect Taxes - Subsidies)."
            )
          )
        ),
        Chapter(
          id = "econ_ch2",
          title = "Ch 2: Monetary Policy & Banking System",
          subtitle = "Repo Rate, CRR, SLR, RBI & Inflation Targeting Framework",
          isPremium = false,
          textContent = listOf(
            "Monetary Policy manages liquidity to ensure price stability while supporting economic growth.",
            "Policy Tools of RBI:",
            "• Quantitative Tools: Repo Rate (rate at which RBI lends to commercial banks), Reverse Repo Rate, Bank Rate, CRR (Cash Reserve Ratio), SLR (Statutory Liquidity Ratio), Open Market Operations (OMO).",
            "• Qualitative Tools: Margin requirements, Moral suasion, Credit rationing.",
            "Flexible Inflation Targeting (FIT):",
            "• Under RBI Act 1934 (amended 2016), Monetary Policy Committee (MPC) sets policy rates to keep CPI inflation at 4% (+/- 2% tolerance band). MPC consists of 6 members (3 RBI, 3 Govt appointed)."
          ),
          flashcards = listOf(
            Flashcard(
              id = "f_econ3",
              question = "What is the composition of India's Monetary Policy Committee (MPC)?",
              answer = "6 members (3 from RBI including Governor as ex-officio Chairperson, and 3 appointed by Central Govt).",
              category = "RBI & Banking"
            )
          ),
          quizQuestions = listOf(
            QuizQuestion(
              id = "q_econ2",
              questionText = "When RBI increases the Cash Reserve Ratio (CRR), it results in:",
              options = listOf("A. Increase in commercial bank lending capacity", "B. Decrease in liquidity available with commercial banks", "C. Decrease in Repo Rate automatically", "D. Increase in foreign direct investment"),
              correctAnswerIndex = 1,
              explanation = "Increasing CRR forces banks to deposit more cash with RBI, sucking liquidity out of the banking system."
            )
          )
        ),
        Chapter(
          id = "econ_ch3",
          title = "Ch 3: Fiscal Policy, Budgeting & FRBM",
          subtitle = "Revenue vs Capital Deficit, Fiscal Deficit & FRBM Act 2003",
          isPremium = true,
          textContent = listOf(
            "Fiscal Policy deals with government spending, taxation, and borrowing strategies.",
            "Deficit Concepts:",
            "1. Fiscal Deficit: Total Expenditure - (Total Receipts excluding Borrowings). Represents total borrowing requirement of Government.",
            "2. Revenue Deficit: Revenue Expenditure - Revenue Receipts. Indicates government living beyond its means for day-to-day consumption.",
            "3. Primary Deficit: Fiscal Deficit - Interest Payments. Measures current fiscal imbalance excluding burden of past loans.",
            "FRBM Act 2003:",
            "• Mandated reduction of Fiscal Deficit to 3% of GDP. NK Singh Committee (2016) recommended debt-to-GDP target of 60% (40% Centre, 20% States)."
          ),
          flashcards = listOf(
            Flashcard(
              id = "f_econ4",
              question = "How is Primary Deficit calculated?",
              answer = "Primary Deficit = Fiscal Deficit - Interest Payments.",
              category = "Fiscal Deficits"
            )
          ),
          quizQuestions = listOf(
            QuizQuestion(
              id = "q_econ3",
              questionText = "Which deficit measure indicates the true total borrowing requirement of the Union Government in a fiscal year?",
              options = listOf("A. Revenue Deficit", "B. Fiscal Deficit", "C. Primary Deficit", "D. Effective Revenue Deficit"),
              correctAnswerIndex = 1,
              explanation = "Fiscal Deficit measures the overall gap between total expenditure and non-debt receipts, representing total market borrowing."
            )
          )
        )
      )
    ),

    // ==========================================
    // 5. ART & CULTURE (Nitin Singhania)
    // ==========================================
    Subject(
      id = "art_culture",
      name = "Art & Culture",
      chapters = listOf(
        Chapter(
          id = "art_ch1",
          title = "Ch 1: Indian Architecture & Sculpture",
          subtitle = "Harappan Seals, Mauryan Pillars, Rock-Cut Caves & Stupas",
          isPremium = false,
          textContent = listOf(
            "Indian architectural evolution spans prehistoric rock art to grand structural temples and Islamic monuments.",
            "Key Phases:",
            "1. Harappan Art: Steatite seals (Pashupati seal), Bronze dancing girl (Lost-wax casting technique), Terracotta figurines (Mother Goddess).",
            "2. Mauryan Art: Court art (Monolithic polished sandstone pillars of Ashoka - Lion Capital at Sarnath) vs Popular art (Yaksha & Yakshini sculptures).",
            "3. Stupa Architecture: Hemispherical earthen mound enclosing Buddha relics. Elements: Harmika, Chhatri, Pradakshina Patha, Toranas (gateway with carvings e.g. Sanchi Stupa).",
            "4. Rock-Cut Caves: Ajanta Caves (29 Buddhist caves - Fresco paintings), Ellora Caves (34 caves - Hindu, Buddhist, Jain; Kailashnath Temple Cave 16 carved top-to-bottom from single rock)."
          ),
          flashcards = listOf(
            Flashcard(
              id = "f_art1",
              question = "Which technique was used to carve the famous Bronze Dancing Girl of Mohenjo-daro?",
              answer = "Lost-Wax Casting technique (Cire Perdue).",
              category = "Harappan Art"
            ),
            Flashcard(
              id = "f_art2",
              question = "Which rock-cut cave is famous for the monolithic top-down carved Kailashnath Temple?",
              answer = "Cave 16 at Ellora, Maharashtra.",
              category = "Cave Architecture"
            )
          ),
          quizQuestions = listOf(
            QuizQuestion(
              id = "q_art1",
              questionText = "Consider the following statements regarding Ajanta and Ellora caves:\n1. Ajanta caves belong exclusively to Buddhism.\n2. Ellora caves contain monuments of Hinduism, Buddhism, and Jainism.\nWhich of the statements given above is/are correct?",
              options = listOf("A. 1 only", "B. 2 only", "C. Both 1 and 2", "D. Neither 1 nor 2"),
              correctAnswerIndex = 2,
              explanation = "Both statements are correct. Ajanta (29 caves) is purely Buddhist, whereas Ellora (34 caves) represents a confluence of Hindu (17), Buddhist (12), and Jain (5) traditions."
            )
          )
        ),
        Chapter(
          id = "art_ch2",
          title = "Ch 2: Temple Architecture Styles",
          subtitle = "Nagara (North), Dravida (South) & Vesara Styles",
          isPremium = false,
          textContent = listOf(
            "Structural temple architecture reached its peak during the Gupta period and matured into distinct regional styles.",
            "Temple Styles:",
            "1. Nagara Style (Northern India): Built on raised platform (Jagati). Features: Garbhagriha (sanctum), Mandapa (hall), Shikhara (tower - Latina, Phamsana, Valabhi types), Amalaka & Kalasha at top. No elaborate boundary walls or Gopurams.",
            "2. Dravida Style (Southern India - Cholas/Pallavas): Features: High boundary walls, grand entrance gateway (Gopuram), Vimana (stepped pyramid tower), Garbhagriha, Kalyana Mandapa, and water tank inside complex (e.g. Brihadeswara Temple Tanjore).",
            "3. Vesara Style (Central/Deccan - Chalukyas & Hoysalas): Hybrid style combining Nagara and Dravida elements (e.g. Chennakeshava temple Belur, Hoysaleswara temple Halebidu)."
          ),
          flashcards = listOf(
            Flashcard(
              id = "f_art3",
              question = "What is the monumental entrance gateway of a Dravida temple called?",
              answer = "Gopuram.",
              category = "Temple Architecture"
            )
          ),
          quizQuestions = listOf(
            QuizQuestion(
              id = "q_art2",
              questionText = "The famous Brihadeswara Temple at Thanjavur was built by which emperor?",
              options = listOf("A. Rajaraja Chola I", "B. Rajendra Chola I", "C. Narasimhavarman I", "D. Amoghavarsha"),
              correctAnswerIndex = 0,
              explanation = "Rajaraja Chola I built the grand Dravidian Brihadeswara Temple dedicated to Lord Shiva at Thanjavur in 1010 CE."
            )
          )
        ),
        Chapter(
          id = "art_ch3",
          title = "Ch 3: Classical Dances & Music",
          subtitle = "8 Classical Dances of India & Hindustani vs Carnatic Music",
          isPremium = true,
          textContent = listOf(
            "Sangeet Natak Akademi recognizes 8 Classical Dance forms of India, rooted in Bharata's Natya Shastra.",
            "Classical Dances:",
            "1. Bharatanatyam (Tamil Nadu): Ekaharya style, Fire dance, Alarippu to Tillana.",
            "2. Kathakali (Kerala): Elaborate makeup, facial expressions, green face (Pacca) for noble characters.",
            "3. Kathak (North India): Footwork (Tatkar), chakkars, Radha-Krishna themes.",
            "4. Odissi (Odisha): Tribhanga posture (three-bend stance), mobile sculpture.",
            "5. Kuchipudi (Andhra Pradesh): Tarangam (dancing on brass plate), Manduka Shabdam.",
            "6. Manipuri (Manipur): Ras Lila, Pung Cholom, gentle fluid movements.",
            "7. Mohiniyattam (Kerala): Dance of enchantress, white/gold Kasavu saree, swaying movements.",
            "8. Sattriya (Assam): Introduced by Srimanta Sankardeva in 15th century Vaishnavite Sattras."
          ),
          flashcards = listOf(
            Flashcard(
              id = "f_art4",
              question = "Which classical dance form features the iconic 'Tribhanga' posture?",
              answer = "Odissi.",
              category = "Classical Dances"
            )
          ),
          quizQuestions = listOf(
            QuizQuestion(
              id = "q_art3",
              questionText = "Sattriya classical dance form was introduced in Assam by which 15th century saint-reformer?",
              options = listOf("A. Chaitanya Mahaprabhu", "B. Srimanta Sankardeva", "C. Madhavdeva", "D. Ramananda"),
              correctAnswerIndex = 1,
              explanation = "Srimanta Sankardeva created Sattriya dance as part of the Neo-Vaishnavite movement in Assam."
            )
          )
        )
      )
    )
  )
}
