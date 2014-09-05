package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Captain of the Watch")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("4WW")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.RARE), @Printings.Printed(ex = Magic2010.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class CaptainoftheWatch extends Card
{
	public static final class MakeSoldiers extends EventTriggeredAbility
	{
		public MakeSoldiers(GameState state)
		{
			super(state, "When Captain of the Watch enters the battlefield, put three 1/1 white Soldier creature tokens onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());
			CreateTokensFactory tokens = new CreateTokensFactory(3, 1, 1, "Put three 1/1 white Soldier creature tokens onto the battlefield.");
			tokens.setColors(Color.WHITE);
			tokens.setSubTypes(SubType.SOLDIER);
			this.addEffect(tokens.getEventFactory());
		}
	}

	public CaptainoftheWatch(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Vigilance (Attacking doesn't cause this creature to tap.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		SetGenerator soldiers = Intersect.instance(HasSubType.instance(SubType.SOLDIER), CREATURES_YOU_CONTROL);
		SetGenerator others = RelativeComplement.instance(soldiers, This.instance());
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, others, "Other Soldier creatures you control", +1, +1, org.rnd.jmagic.abilities.keywords.Vigilance.class, true));

		this.addAbility(new MakeSoldiers(state));
	}
}
