package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Avatar of Woe")
@Types({Type.CREATURE})
@SubTypes({SubType.AVATAR})
@ManaCost("6BB")
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.RARE), @Printings.Printed(ex = TimeSpiral.class, r = Rarity.SPECIAL), @Printings.Printed(ex = Prophecy.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class AvatarofWoe extends Card
{
	public static final class AvatarofWoeAbility0 extends StaticAbility
	{
		public AvatarofWoeAbility0(GameState state)
		{
			super(state, "If there are ten or more creature cards total in all graveyards, Avatar of Woe costs (6) less to cast.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MANA_COST_REDUCTION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.fromCollection(new ManaPool("(6)")));

			this.addEffectPart(part);

			SetGenerator creatureCards = Intersect.instance(HasType.instance(Type.CREATURE), Cards.instance());
			SetGenerator inGraveyards = InZone.instance(GraveyardOf.instance(Players.instance()));
			SetGenerator count = Count.instance(Intersect.instance(inGraveyards, creatureCards));

			// doesn't union with this.canApply since it completely changes when
			// it applies
			this.canApply = Intersect.instance(count, Between.instance(10, null));
		}
	}

	public static final class AvatarofWoeAbility2 extends ActivatedAbility
	{
		public AvatarofWoeAbility2(GameState state)
		{
			super(state, "(T): Destroy target creature. It can't be regenerated.");
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffects(bury(this, target, "Destroy target creature. It can't be regenerated."));
		}
	}

	public AvatarofWoe(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(5);

		// If there are ten or more creature cards total in all graveyards,
		// Avatar of Woe costs (6) less to cast.
		this.addAbility(new AvatarofWoeAbility0(state));

		// Fear (This creature can't be blocked except by artifact creatures
		// and/or black creatures.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Fear(state));

		// (T): Destroy target creature. It can't be regenerated.
		this.addAbility(new AvatarofWoeAbility2(state));
	}
}
