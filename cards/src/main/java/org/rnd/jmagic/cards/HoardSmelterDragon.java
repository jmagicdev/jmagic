package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hoard-Smelter Dragon")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("4RR")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class HoardSmelterDragon extends Card
{
	public static final class HoardSmelterDragonAbility1 extends ActivatedAbility
	{
		public HoardSmelterDragonAbility1(GameState state)
		{
			super(state, "(3)(R): Destroy target artifact. Hoard-Smelter Dragon gets +X/+0 until end of turn, where X is that artifact's converted mana cost.");
			this.setManaCost(new ManaPool("(3)(R)"));
			SetGenerator target = targetedBy(this.addTarget(ArtifactPermanents.instance(), "target artifact"));
			this.addEffect(destroy(target, "Destroy target artifact."));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, ConvertedManaCostOf.instance(target), numberGenerator(0), "Hoard-Smelter Dragon gets +X/+0 until end of turn, where X is that artifact's converted mana cost."));
		}
	}

	public HoardSmelterDragon(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (3)(R): Destroy target artifact. Hoard-Smelter Dragon gets +X/+0
		// until end of turn, where X is that artifact's converted mana cost.
		this.addAbility(new HoardSmelterDragonAbility1(state));
	}
}
