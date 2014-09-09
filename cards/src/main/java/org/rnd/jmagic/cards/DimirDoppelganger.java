package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dimir Doppelganger")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAPESHIFTER})
@ManaCost("1UB")
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class DimirDoppelganger extends Card
{
	public static final class DimirDoppelgangerAbility0 extends ActivatedAbility
	{
		public DimirDoppelgangerAbility0(GameState state)
		{
			super(state, "(1)(U)(B): Exile target creature card from a graveyard. Dimir Doppelganger becomes a copy of that card and gains this ability.");
			this.setManaCost(new ManaPool("(1)(U)(B)"));

			Target target = this.addTarget(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(Players.instance()))), "target creature card from a graveyard");

			this.addEffect(exile(targetedBy(target), "Exile target creature card from a graveyard."));

			ContinuousEffect.Part copyPart = new ContinuousEffect.Part(ContinuousEffectType.COPY_OBJECT);
			copyPart.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(new org.rnd.jmagic.engine.SimpleAbilityFactory(DimirDoppelgangerAbility0.class)));
			copyPart.parameters.put(ContinuousEffectType.Parameter.ORIGINAL, targetedBy(target));
			copyPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);

			this.addEffect(createFloatingEffect(Empty.instance(), "Dimir Doppelganger becomes a copy of that card and gains this ability.", copyPart));
		}
	}

	public DimirDoppelganger(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(2);

		this.addAbility(new DimirDoppelgangerAbility0(state));
	}
}
