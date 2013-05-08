package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Merfolk Spy")
@Types({Type.CREATURE})
@SubTypes({SubType.MERFOLK, SubType.ROGUE})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class MerfolkSpy extends Card
{
	public static final class MerfolkSpyAbility1 extends EventTriggeredAbility
	{
		public MerfolkSpyAbility1(GameState state)
		{
			super(state, "Whenever Merfolk Spy deals combat damage to a player, that player reveals a card at random from his or her hand.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());

			SetGenerator thatPlayer = TakerOfDamage.instance(TriggerDamage.instance(This.instance()));

			EventFactory reveal = new EventFactory(EventType.REVEAL_RANDOM_FROM_HAND, "That player reveals a card at random from his or her hand.");
			reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
			reveal.parameters.put(EventType.Parameter.PLAYER, thatPlayer);
			reveal.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			this.addEffect(reveal);
		}
	}

	public MerfolkSpy(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Islandwalk (This creature is unblockable as long as defending player
		// controls an Island.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Islandwalk(state));

		// Whenever Merfolk Spy deals combat damage to a player, that player
		// reveals a card at random from his or her hand.
		this.addAbility(new MerfolkSpyAbility1(state));
	}
}
