package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Viashino Heretic")
@Types({Type.CREATURE})
@SubTypes({SubType.VIASHINO})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.URZAS_LEGACY, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class ViashinoHeretic extends Card
{
	public static final class ViashinoHereticAbility0 extends ActivatedAbility
	{
		public ViashinoHereticAbility0(GameState state)
		{
			super(state, "(1)(R), (T): Destroy target artifact. Viashino Heretic deals damage to that artifact's controller equal to the artifact's converted mana cost.");
			this.setManaCost(new ManaPool("(1)(R)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(ArtifactPermanents.instance(), "target artifact"));
			this.addEffect(destroy(target, "Destroy target artifact."));
			this.addEffect(permanentDealDamage(ConvertedManaCostOf.instance(target), ControllerOf.instance(target), "Viashino Heretic deals damage to that artifact's controller equal to the artifact's converted mana cost."));
		}
	}

	public ViashinoHeretic(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// (1)(R), (T): Destroy target artifact. Viashino Heretic deals damage
		// to that artifact's controller equal to the artifact's converted mana
		// cost.
		this.addAbility(new ViashinoHereticAbility0(state));
	}
}
