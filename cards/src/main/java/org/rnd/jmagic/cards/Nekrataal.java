package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Nekrataal")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ASSASSIN})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Visions.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class Nekrataal extends Card
{
	public static final class CIPTerror extends EventTriggeredAbility
	{
		public CIPTerror(GameState state)
		{
			super(state, "When Nekrataal enters the battlefield, destroy target nonartifact, nonblack creature. That creature can't be regenerated.");

			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(RelativeComplement.instance(RelativeComplement.instance(CreaturePermanents.instance(), HasType.instance(Type.ARTIFACT)), HasColor.instance(Color.BLACK)), "target nonblack creature");

			this.addEffects(bury(this, targetedBy(target), "Destroy target nonartifact, nonblack creature. That creature can't be regenerated."));
		}
	}

	public Nekrataal(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
		this.addAbility(new CIPTerror(state));
	}
}
