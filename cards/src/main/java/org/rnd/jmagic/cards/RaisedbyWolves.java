package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Raised by Wolves")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("3GG")
@ColorIdentity({Color.GREEN})
public final class RaisedbyWolves extends Card
{
	public static final class RaisedbyWolvesAbility1 extends EventTriggeredAbility
	{
		public RaisedbyWolvesAbility1(GameState state)
		{
			super(state, "When Raised by Wolves enters the battlefield, put two 2/2 green Wolf creature tokens onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			CreateTokensFactory wolves = new CreateTokensFactory(2, 2, 2, "Put two 2/2 green Wolf creature tokens onto the battlefield.");
			wolves.setColors(Color.GREEN);
			wolves.setSubTypes(SubType.WOLF);
			this.addEffect(wolves.getEventFactory());
		}
	}

	public static final class RaisedbyWolvesAbility2 extends StaticAbility
	{
		public RaisedbyWolvesAbility2(GameState state)
		{
			super(state, "Enchanted creature gets +1/+1 for each Wolf you control.");

			SetGenerator num = Count.instance(Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.WOLF)));
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), num, num));
		}
	}

	public RaisedbyWolves(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// When Raised by Wolves enters the battlefield, put two 2/2 green Wolf
		// creature tokens onto the battlefield.
		this.addAbility(new RaisedbyWolvesAbility1(state));

		// Enchanted creature gets +1/+1 for each Wolf you control.
		this.addAbility(new RaisedbyWolvesAbility2(state));
	}
}
