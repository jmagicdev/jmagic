package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Voltaic Construct")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.GOLEM, SubType.CONSTRUCT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.DARKSTEEL, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class VoltaicConstruct extends Card
{
	public static final class VoltaicConstructAbility0 extends ActivatedAbility
	{
		public VoltaicConstructAbility0(GameState state)
		{
			super(state, "(2): Untap target artifact creature.");
			this.setManaCost(new ManaPool("(2)"));
			Target target = this.addTarget(Intersect.instance(ArtifactPermanents.instance(), CreaturePermanents.instance()), "target artifact creature");
			this.addEffect(untap(targetedBy(target), "Untap target artifact creature."));
		}
	}

	public VoltaicConstruct(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (2): Untap target artifact creature.
		this.addAbility(new VoltaicConstructAbility0(state));
	}
}
