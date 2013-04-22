package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Anowon, the Ruin Sage")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.VAMPIRE})
@ManaCost("3BB")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class AnowontheRuinSage extends Card
{
	public static final class AnowontheRuinSageAbility0 extends EventTriggeredAbility
	{
		public AnowontheRuinSageAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, each player sacrifices a non-Vampire creature.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator nonVampireCreatures = RelativeComplement.instance(CreaturePermanents.instance(), HasSubType.instance(SubType.VAMPIRE));
			this.addEffect(sacrifice(Players.instance(), 1, nonVampireCreatures, "Each player sacrifices a non-Vampire creature."));
		}
	}

	public AnowontheRuinSage(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		// At the beginning of your upkeep, each player sacrifices a non-Vampire
		// creature.
		this.addAbility(new AnowontheRuinSageAbility0(state));
	}
}
