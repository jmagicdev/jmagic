package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.abilities.keywords.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Golem Artisan")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.GOLEM})
@ManaCost("5")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class GolemArtisan extends Card
{
	public static final PlayerInterface.ChooseReason GOLEM_ARTISAN_REASON = new PlayerInterface.ChooseReason("GolemArtisan", "Choose an ability.", true);

	public static final class GolemArtisanAbility0 extends ActivatedAbility
	{
		public GolemArtisanAbility0(GameState state)
		{
			super(state, "(2): Target artifact creature gets +1/+1 until end of turn.");
			this.setManaCost(new ManaPool("(2)"));
			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(ArtifactPermanents.instance(), CreaturePermanents.instance()), "target artifact creature"));
			this.addEffect(createFloatingEffect("Target artifact creature gets +1/+1 until end of turn.", modifyPowerAndToughness(target, +1, +1)));
		}
	}

	public static final class GolemArtisanAbility1 extends ActivatedAbility
	{
		public GolemArtisanAbility1(GameState state)
		{
			super(state, "(2): Target artifact creature gains your choice of flying, trample, or haste until end of turn.");
			this.setManaCost(new ManaPool("(2)"));
			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(ArtifactPermanents.instance(), CreaturePermanents.instance()), "target artifact creature"));

			java.util.Map<Class<? extends Keyword>, AbilityFactory> map = new java.util.HashMap<Class<? extends Keyword>, AbilityFactory>();
			map.put(Flying.class, new org.rnd.jmagic.engine.SimpleAbilityFactory(Flying.class));
			map.put(Trample.class, new org.rnd.jmagic.engine.SimpleAbilityFactory(Trample.class));
			map.put(Haste.class, new org.rnd.jmagic.engine.SimpleAbilityFactory(Haste.class));

			EventFactory choose = new EventFactory(EventType.PLAYER_CHOOSE, "");
			choose.parameters.put(EventType.Parameter.PLAYER, You.instance());
			choose.parameters.put(EventType.Parameter.CHOICE, MapKeySet.instance(map));
			choose.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.CLASS, GOLEM_ARTISAN_REASON));
			this.addEffect(choose);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_ABILITY_TO_OBJECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, target);
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, MapGet.instance(EffectResult.instance(choose), map));
			this.addEffect(createFloatingEffect("Target artifact creature gains your choice of flying, trample, or haste until end of turn.", part));
		}
	}

	public GolemArtisan(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// (2): Target artifact creature gets +1/+1 until end of turn.
		this.addAbility(new GolemArtisanAbility0(state));

		// (2): Target artifact creature gains your choice of flying, trample,
		// or haste until end of turn.
		this.addAbility(new GolemArtisanAbility1(state));
	}
}
