package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Ajani Vengeant")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.AJANI})
@ManaCost("2RW")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class AjaniVengeant extends Card
{
	public static final class IsMildlyAnnoyed extends LoyaltyAbility
	{
		public IsMildlyAnnoyed(GameState state)
		{
			super(state, +1, "Target permanent doesn't untap during its controller's next untap step.");
			Target target = this.addTarget(Permanents.instance(), "target permanent");

			EventPattern untapping = new UntapDuringControllersUntapStep(targetedBy(target));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(untapping));

			SetGenerator thatPlayersUntap = UntapStepOf.instance(ControllerOf.instance(targetedBy(target)));
			SetGenerator untapStepOver = Intersect.instance(PreviousStep.instance(), thatPlayersUntap);

			this.addEffect(createFloatingEffect(untapStepOver, "Target permanent doesn't untap during its controller's next untap step.", part));
		}
	}

	public static final class LightningHelix extends LoyaltyAbility
	{
		public LightningHelix(GameState state)
		{
			super(state, -2, "Ajani Vengeant deals 3 damage to target creature or player and you gain 3 life.");
			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
			this.addEffect(permanentDealDamage(3, targetedBy(target), "Ajani Vengeant deals 3 damage to target creature or player"));
			this.addEffect(gainLife(You.instance(), 3, "and you gain 3 life."));
		}
	}

	public static final class IsFuckingPissed extends LoyaltyAbility
	{
		public IsFuckingPissed(GameState state)
		{
			super(state, -7, "Destroy all lands target player controls.");
			Target target = this.addTarget(Players.instance(), "target player");
			this.addEffect(destroy(Intersect.instance(HasType.instance(Type.LAND), ControlledBy.instance(targetedBy(target))), "Destroy all lands target player controls."));
		}
	}

	public AjaniVengeant(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(3);

		// +1: Target permanent doesn't untap during its controller's next untap
		// step.
		this.addAbility(new IsMildlyAnnoyed(state));

		// -2: Ajani Vengeant deals 3 damage to target creature or player and
		// you gain 3 life.
		this.addAbility(new LightningHelix(state));

		// -7: Destroy all lands target player controls.
		this.addAbility(new IsFuckingPissed(state));
	}
}
