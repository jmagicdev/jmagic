package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lone Revenant")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("3UU")
@ColorIdentity({Color.BLUE})
public final class LoneRevenant extends Card
{
	public static final class LoneRevenantAbility1 extends EventTriggeredAbility
	{
		public LoneRevenantAbility1(GameState state)
		{
			super(state, "Whenever Lone Revenant deals combat damage to a player, if you control no other creatures, look at the top four cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());
			this.interveningIf = Intersect.instance(numberGenerator(0), Count.instance(RelativeComplement.instance(Intersect.instance(ControlledBy.instance(You.instance()), HasType.instance(Type.CREATURE)), ABILITY_SOURCE_OF_THIS)));

			EventFactory factory = new EventFactory(LOOK_AT_THE_TOP_N_CARDS_PUT_ONE_INTO_HAND_AND_THE_REST_ON_BOTTOM, "Look at the top four cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(4));
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.ZONE, LibraryOf.instance(You.instance()));
			this.addEffect(factory);
		}
	}

	public LoneRevenant(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Hexproof (This creature can't be the target of spells or abilities
		// your opponents control.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Hexproof(state));

		// Whenever Lone Revenant deals combat damage to a player, if you
		// control no other creatures, look at the top four cards of your
		// library. Put one of them into your hand and the rest on the bottom of
		// your library in any order.
		this.addAbility(new LoneRevenantAbility1(state));
	}
}
