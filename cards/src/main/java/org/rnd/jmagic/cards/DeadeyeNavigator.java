package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Deadeye Navigator")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("4UU")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class DeadeyeNavigator extends Card
{
	public static final class DeadeyeNavigatorAbility1 extends ActivatedAbility
	{
		public DeadeyeNavigatorAbility1(GameState state)
		{
			super(state, "(1)(U): Exile this creature, then return it to the battlefield under your control.");
			this.setManaCost(new ManaPool("(1)(U)"));
			EventFactory factory = new EventFactory(BLINK, "Exile this creature, then return it to the battlefield under your control.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.TARGET, ABILITY_SOURCE_OF_THIS);
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(factory);
		}
	}

	public DeadeyeNavigator(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Soulbond (You may pair this creature with another unpaired creature
		// when either enters the battlefield. They remain paired for as long as
		// you control both of them.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Soulbond(state));

		// As long as Deadeye Navigator is paired with another creature, each of
		// those creatures has
		// "(1)(U): Exile this creature, then return it to the battlefield under your control."
		this.addAbility(new org.rnd.jmagic.abilities.AbilityIfPaired.Final(state, "As long as Deadeye Navigator is paired with another creature, each of those creatures has \"(1)(U): Exile this creature, then return it to the battlefield under your control.\"", DeadeyeNavigatorAbility1.class));
	}
}
