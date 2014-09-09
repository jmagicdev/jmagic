package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Myr Welder")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.MYR})
@ManaCost("3")
@ColorIdentity({})
public final class MyrWelder extends Card
{
	public static final class MyrWelderAbility0 extends ActivatedAbility
	{
		public MyrWelderAbility0(GameState state)
		{
			super(state, "(T): Exile target artifact card from a graveyard.");
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.ARTIFACT), InZone.instance(GraveyardOf.instance(You.instance()))), "target artifact card in a graveyard"));
			EventFactory exile = exile(target, "Exile target artifact card from a graveyard.");
			exile.setLink(this);
			this.addEffect(exile);

			this.getLinkManager().addLinkClass(MyrWelderAbility1.class);
		}
	}

	public static final class MyrWelderAbility1 extends StaticAbility
	{
		public MyrWelderAbility1(GameState state)
		{
			super(state, "Myr Welder has all activated abilities of all cards exiled with it.");

			SetGenerator abilities = ActivatedAbilitiesOf.instance(ChosenFor.instance(LinkedTo.instance(Identity.instance(this))));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COPY_ABILITIES_TO_OBJECT);
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, abilities);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			this.addEffectPart(part);

			this.getLinkManager().addLinkClass(MyrWelderAbility0.class);
		}
	}

	public MyrWelder(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);

		// Imprint \u2014 (T): Exile target artifact card from a graveyard.
		this.addAbility(new MyrWelderAbility0(state));

		// Myr Welder has all activated abilities of all cards exiled with it.
		this.addAbility(new MyrWelderAbility1(state));
	}
}
