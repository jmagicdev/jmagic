package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Hero of Bladehold")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.KNIGHT})
@ManaCost("2WW")
@ColorIdentity({Color.WHITE})
public final class HeroofBladehold extends Card
{
	public static final class HeroofBladeholdAbility1 extends EventTriggeredAbility
	{
		public HeroofBladeholdAbility1(GameState state)
		{
			super(state, "Whenever Hero of Bladehold attacks, put two 1/1 white Soldier creature tokens onto the battlefield tapped and attacking.");
			this.addPattern(whenThisAttacks());

			CreateTokensFactory f = new CreateTokensFactory(2, 1, 1, "Put two 1/1 white Soldier creature tokens onto the battlefield tapped and attacking.");
			f.setColors(Color.WHITE);
			f.setSubTypes(SubType.SOLDIER);
			f.setTappedAndAttacking(null);
			this.addEffect(f.getEventFactory());
		}
	}

	public HeroofBladehold(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Battle cry (Whenever this creature attacks, each other attacking
		// creature gets +1/+0 until end of turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.BattleCry(state));

		// Whenever Hero of Bladehold attacks, put two 1/1 white Soldier
		// creature tokens onto the battlefield tapped and attacking.
		this.addAbility(new HeroofBladeholdAbility1(state));
	}
}
