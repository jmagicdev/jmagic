package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Grove of the Guardian")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class GroveoftheGuardian extends Card
{
	public static final class GroveoftheGuardianAbility1 extends ActivatedAbility
	{
		public GroveoftheGuardianAbility1(GameState state)
		{
			super(state, "(3)(G)(W), (T), Tap two untapped creatures you control, Sacrifice Grove of the Guardian: Put an 8/8 green and white Elemental creature token with vigilance onto the battlefield.");
			this.setManaCost(new ManaPool("(3)(G)(W)"));
			this.costsTap = true;

			// Tap two untapped creatures you control
			EventFactory cost = new EventFactory(EventType.TAP_CHOICE, "Tap two untapped creatures you control");
			cost.parameters.put(EventType.Parameter.CAUSE, This.instance());
			cost.parameters.put(EventType.Parameter.PLAYER, You.instance());
			cost.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(ControlledBy.instance(You.instance()), HasType.instance(Type.CREATURE), Untapped.instance()));
			cost.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			this.addCost(cost);

			this.addCost(sacrificeThis("Grove of the Guardian"));

			CreateTokensFactory factory = new CreateTokensFactory(1, 8, 8, "Put an 8/8 green and white Elemental creature token with vigilance onto the battlefield.");
			factory.setColors(Color.GREEN, Color.WHITE);
			factory.setSubTypes(SubType.ELEMENTAL);
			factory.addAbility(org.rnd.jmagic.abilities.keywords.Vigilance.class);
			this.addEffect(factory.getEventFactory());
		}
	}

	public GroveoftheGuardian(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (3)(G)(W), (T), Tap two untapped creatures you control, Sacrifice
		// Grove of the Guardian: Put an 8/8 green and white Elemental creature
		// token with vigilance onto the battlefield.
		this.addAbility(new GroveoftheGuardianAbility1(state));
	}
}
