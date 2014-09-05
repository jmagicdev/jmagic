package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Cranial Plating")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = FifthDawn.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class CranialPlating extends Card
{
	public static final class ArtifactPump extends StaticAbility
	{
		public ArtifactPump(GameState state)
		{
			super(state, "Equipped creature gets +1/+0 for each artifact you control.");

			SetGenerator amount = Count.instance(Intersect.instance(ArtifactPermanents.instance(), ControlledBy.instance(You.instance())));
			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), amount, numberGenerator(0)));
		}
	}

	public static final class CranialPlatingAbility1 extends ActivatedAbility
	{
		public CranialPlatingAbility1(GameState state)
		{
			super(state, "(B)(B): Attach Cranial Plating to target creature you control.");
			this.setManaCost(new ManaPool("(B)(B)"));

			Target t = this.addTarget(CREATURES_YOU_CONTROL, "target creature you control");
			this.addEffect(attach(ABILITY_SOURCE_OF_THIS, targetedBy(t), "Attach Cranial Plating to target creature you control."));
		}
	}

	public CranialPlating(GameState state)
	{
		super(state);

		// Equipped creature gets +1/+0 for each artifact you control.
		this.addAbility(new ArtifactPump(state));

		// (B)(B): Attach Cranial Plating to target creature you control.
		this.addAbility(new CranialPlatingAbility1(state));

		// Equip (1)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(1)"));
	}
}
