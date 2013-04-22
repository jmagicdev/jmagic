package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dark Impostor")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE, SubType.ASSASSIN})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class DarkImpostor extends Card
{
	public static final class DarkImpostorAbility0 extends ActivatedAbility
	{
		public DarkImpostorAbility0(GameState state)
		{
			super(state, "(4)(B)(B): Exile target creature and put a +1/+1 counter on Dark Impostor.");
			this.setManaCost(new ManaPool("(4)(B)(B)"));

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

			EventFactory exile = exile(target, "Exile target creature");
			exile.setLink(this);
			this.addEffect(exile);

			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "and put a +1/+1 counter on Dark Impostor."));

			this.getLinkManager().addLinkClass(DarkImpostorAbility1.class);
		}
	}

	public static final class DarkImpostorAbility1 extends StaticAbility
	{
		public DarkImpostorAbility1(GameState state)
		{
			super(state, "Dark Impostor has all activated abilities of all creature cards exiled with it.");

			SetGenerator abilities = ActivatedAbilitiesOf.instance(ChosenFor.instance(LinkedTo.instance(Identity.instance(this))));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COPY_ABILITIES_TO_OBJECT);
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, abilities);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			this.addEffectPart(part);

			this.getLinkManager().addLinkClass(DarkImpostorAbility0.class);
		}
	}

	public DarkImpostor(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (4)(B)(B): Exile target creature and put a +1/+1 counter on Dark
		// Impostor.
		this.addAbility(new DarkImpostorAbility0(state));

		// Dark Impostor has all activated abilities of all creature cards
		// exiled with it.
		this.addAbility(new DarkImpostorAbility1(state));
	}
}
