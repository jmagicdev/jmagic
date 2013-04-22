package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Howlpack Alpha")
@Types({Type.CREATURE})
@SubTypes({SubType.WEREWOLF})
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class HowlpackAlpha extends AlternateCard
{
	public static final class HowlpackAlphaAbility0 extends StaticAbility
	{
		public HowlpackAlphaAbility0(GameState state)
		{
			super(state, "Other Werewolf and Wolf creatures you control get +1/+1.");

			this.addEffectPart(modifyPowerAndToughness(RelativeComplement.instance(Intersect.instance(HasSubType.instance(SubType.WEREWOLF, SubType.WOLF), CREATURES_YOU_CONTROL), This.instance()), +1, +1));
		}
	}

	public static final class HowlpackAlphaAbility1 extends EventTriggeredAbility
	{
		public HowlpackAlphaAbility1(GameState state)
		{
			super(state, "At the beginning of your end step, put a 2/2 green Wolf creature token onto the battlefield.");
			this.addPattern(atTheBeginningOfYourEndStep());

			CreateTokensFactory factory = new CreateTokensFactory(1, 2, 2, "Put a 2/2 green Wolf creature token onto the battlefield.");
			factory.setColors(Color.GREEN);
			factory.setSubTypes(SubType.WOLF);
			this.addEffect(factory.getEventFactory());
		}
	}

	public HowlpackAlpha(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.setColorIndicator(Color.GREEN);

		// Other Werewolf and Wolf creatures you control get +1/+1.
		this.addAbility(new HowlpackAlphaAbility0(state));

		// At the beginning of your end step, put a 2/2 green Wolf creature
		// token onto the battlefield.
		this.addAbility(new HowlpackAlphaAbility1(state));

		// At the beginning of each upkeep, if a player cast two or more spells
		// last turn, transform Howlpack Alpha.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeHuman(state, this.getName()));
	}
}
