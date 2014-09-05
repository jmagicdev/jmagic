package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Thunder Brute")
@Types({Type.CREATURE})
@SubTypes({SubType.CYCLOPS})
@ManaCost("4RR")
@Printings({@Printings.Printed(ex = BornOfTheGods.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class ThunderBrute extends Card
{
	public static final class IfNotBigThenFast extends EventTriggeredAbility
	{
		public IfNotBigThenFast(GameState state)
		{
			super(state, "When Thunder Brute enters the battlefield, if tribute wasn't paid, it gains haste until end of turn.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.interveningIf = org.rnd.jmagic.abilities.keywords.Tribute.WasntPaid.instance();
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Haste.class, "Thunder Brute gains haste until end of turn."));
		}
	}

	public ThunderBrute(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Tribute(state, 3));

		this.addAbility(new IfNotBigThenFast(state));
	}
}
