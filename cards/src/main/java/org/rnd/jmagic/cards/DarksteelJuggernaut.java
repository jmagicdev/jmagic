package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Darksteel Juggernaut")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.JUGGERNAUT})
@ManaCost("5")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({})
public final class DarksteelJuggernaut extends Card
{
	public static final class DarksteelCDA extends CharacteristicDefiningAbility
	{
		public DarksteelCDA(GameState state)
		{
			super(state, "Darksteel Juggernaut's power and toughness are each equal to the number of artifacts you control.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator count = Count.instance(Intersect.instance(ArtifactPermanents.instance(), ControlledBy.instance(You.instance())));

			this.addEffectPart(setPowerAndToughness(This.instance(), count, count));
		}
	}

	public static final class DarksteelFaceWrecker extends StaticAbility
	{
		public DarksteelFaceWrecker(GameState state)
		{
			super(state, "Darksteel Juggernaut is indestructible and attacks each turn if able.");

			this.addEffectPart(indestructible(This.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_REQUIREMENT);
			part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, This.instance());
			this.addEffectPart(part);
		}
	}

	public DarksteelJuggernaut(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Darksteel Juggernaut's power and toughness are each equal to the
		// number of artifacts you control.
		this.addAbility(new DarksteelCDA(state));

		// Darksteel Juggernaut is indestructible and attacks each turn if able.
		this.addAbility(new DarksteelFaceWrecker(state));
	}
}
