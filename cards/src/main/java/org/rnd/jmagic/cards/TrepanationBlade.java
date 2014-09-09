package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Trepanation Blade")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("3")
@ColorIdentity({})
public final class TrepanationBlade extends Card
{
	public static final class TrepanationBladeAbility0 extends EventTriggeredAbility
	{
		public TrepanationBladeAbility0(GameState state)
		{
			super(state, "Whenever equipped creature attacks, defending player reveals cards from the top of his or her library until he or she reveals a land card. The creature gets +1/+0 until end of turn for each card revealed this way. That player puts the revealed cards into his or her graveyard.");

			SetGenerator equippedCreature = EquippedBy.instance(ABILITY_SOURCE_OF_THIS);

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			pattern.put(EventType.Parameter.OBJECT, equippedCreature);
			this.addPattern(pattern);

			SetGenerator defendingPlayer = DefendingPlayer.instance(equippedCreature);
			SetGenerator defendingLibrary = LibraryOf.instance(defendingPlayer);
			SetGenerator toReveal = TopMost.instance(defendingLibrary, numberGenerator(1), HasType.instance(Type.LAND));

			EventFactory reveal = reveal(toReveal, "Defending player reveals cards from the top of his or her library until he or she reveals a land card.");
			this.addEffect(reveal);

			SetGenerator revealedCards = EffectResult.instance(reveal);
			this.addEffect(ptChangeUntilEndOfTurn(equippedCreature, Count.instance(revealedCards), numberGenerator(0), "The creature gets +1/+0 until end of turn for each card revealed this way."));

			this.addEffect(putIntoGraveyard(revealedCards, "That player puts the revealed cards into his or her graveyard."));
		}
	}

	public TrepanationBlade(GameState state)
	{
		super(state);

		// Whenever equipped creature attacks, defending player reveals cards
		// from the top of his or her library until he or she reveals a land
		// card. The creature gets +1/+0 until end of turn for each card
		// revealed this way. That player puts the revealed cards into his or
		// her graveyard.
		this.addAbility(new TrepanationBladeAbility0(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
