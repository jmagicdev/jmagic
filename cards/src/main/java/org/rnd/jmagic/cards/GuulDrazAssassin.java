package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Guul Draz Assassin")
@Types({Type.CREATURE})
@SubTypes({SubType.ASSASSIN, SubType.VAMPIRE})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class GuulDrazAssassin extends Card
{
	public static final class GuulDrazAssassinAbility3 extends ActivatedAbility
	{
		public GuulDrazAssassinAbility3(GameState state)
		{
			super(state, "(B), (T): Target creature gets -2/-2 until end of turn.");
			this.setManaCost(new ManaPool("(B)"));
			this.costsTap = true;

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			this.addEffect(createFloatingEffect("Target creature gets -2/-2 until end of turn.", modifyPowerAndToughness(targetedBy(target), -2, -2)));
		}
	}

	public static final class GuulDrazAssassinAbility6 extends ActivatedAbility
	{
		public GuulDrazAssassinAbility6(GameState state)
		{
			super(state, "(B), (T): Target creature gets -4/-4 until end of turn.");
			this.setManaCost(new ManaPool("(B)"));
			this.costsTap = true;

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			this.addEffect(createFloatingEffect("Target creature gets -4/-4 until end of turn.", modifyPowerAndToughness(targetedBy(target), -4, -4)));
		}
	}

	public GuulDrazAssassin(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Level up (1)(B) ((1)(B): Put a level counter on this. Level up only
		// as a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.LevelUp(state, "(1)(B)"));

		// LEVEL 2-3
		// 2/2
		// (B), (T): Target creature gets -2/-2 until end of turn.
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 2, 3, 2, 2, "\"(B), (T): Target creature gets -2/-2 until end of turn.\"", GuulDrazAssassinAbility3.class));

		// LEVEL 4+
		// 4/4
		// (B), (T): Target creature gets -4/-4 until end of turn.
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 4, 4, 4, "\"(B), (T): Target creature gets -4/-4 until end of turn.\"", GuulDrazAssassinAbility6.class));
	}
}
