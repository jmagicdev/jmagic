package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vengeful Archon")
@Types({Type.CREATURE})
@SubTypes({SubType.ARCHON})
@ManaCost("4WWW")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class VengefulArchon extends Card
{
	public static final class VengefulArchonAbility1 extends ActivatedAbility
	{
		public static final class VengefulArchonEffect extends DamageReplacementEffect
		{
			private final int target;

			public VengefulArchonEffect(Game game, String name, Player target)
			{
				super(game, name);
				this.target = target.ID;
				this.makePreventionEffect();
			}

			@Override
			public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
			{
				Player you = ((GameObject)this.getSourceObject(context.game.actualState)).getController(context.game.actualState);

				DamageAssignment.Batch ret = new DamageAssignment.Batch();
				for(DamageAssignment damage: damageAssignments)
					if(you.ID == damage.takerID)
						ret.add(damage);
				return ret;
			}

			@Override
			public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
			{
				if(damageAssignments.isEmpty())
					return java.util.Collections.emptyList();

				FloatingContinuousEffect fce = this.getFloatingContinuousEffect(this.game.physicalState);
				if(fce.damage == 0)
					return java.util.Collections.emptyList();

				// This will never get more damage than it can prevent
				int prevented = damageAssignments.size();
				fce.damage -= prevented;
				damageAssignments.clear();
				return java.util.Collections.singletonList(permanentDealDamage(prevented, Identity.instance(this.game.actualState.get(this.target)), "Vengeful Archon deals that much damage to target player."));
			}

			@Override
			public java.util.Collection<GameObject> refersTo(GameState state)
			{
				return java.util.Collections.singleton((GameObject)this.getSourceObject(state));
			}
		}

		/**
		 * @eparam CAUSE: refraction trap
		 * @eparam PLAYER: controller of CAUSE
		 * @eparam NUMBER: the amount of damage to prevent
		 * @eparam OBJECT: permanents controlled by PLAYER
		 * @eparam TARGET: creature or player targeted by CAUSE
		 */
		public static final EventType VENGEFUL_ARCHON_EVENT = new EventType("VENGEFUL_ARCHON_EVENT")
		{
			@Override
			public Parameter affects()
			{
				return null;
			}

			@Override
			public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
			{
				final Player target = parameters.get(Parameter.TARGET).getOne(Player.class);
				final int number = Sum.get(parameters.get(Parameter.NUMBER));

				DamageReplacementEffect replacement = new VengefulArchonEffect(game, "Prevent the next X damage that would be dealt to you this turn. If damage is prevented this way, Vengeful Archon deals that much damage to target player.", target);

				ContinuousEffect.Part part = replacementEffectPart(replacement);

				java.util.Map<Parameter, Set> fceParameters = new java.util.HashMap<Parameter, Set>();
				fceParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
				fceParameters.put(Parameter.EFFECT, new Set(part));
				fceParameters.put(Parameter.DAMAGE, new Set(number));
				Event createFce = createEvent(game, "Prevent the next X damage that would be dealt to you this turn. If damage is prevented this way, Vengeful Archon deals that much damage to target player.", CREATE_FLOATING_CONTINUOUS_EFFECT, fceParameters);
				createFce.perform(event, true);

				event.setResult(Empty.set);
				return true;
			}
		};

		public VengefulArchonAbility1(GameState state)
		{
			super(state, "(X): Prevent the next X damage that would be dealt to you this turn. If damage is prevented this way, Vengeful Archon deals that much damage to target player.");
			this.setManaCost(new ManaPool("(X)"));

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

			EventFactory effect = new EventFactory(VENGEFUL_ARCHON_EVENT, "Prevent the next X damage that would be dealt to you this turn. If damage is prevented this way, Vengeful Archon deals that much damage to target player.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
			effect.parameters.put(EventType.Parameter.NUMBER, ValueOfX.instance(This.instance()));
			effect.parameters.put(EventType.Parameter.OBJECT, ControlledBy.instance(You.instance()));
			effect.parameters.put(EventType.Parameter.TARGET, target);
			this.addEffect(effect);
		}
	}

	public VengefulArchon(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (X): Prevent the next X damage that would be dealt to you this turn.
		// If damage is prevented this way, Vengeful Archon deals that much
		// damage to target player.
		this.addAbility(new VengefulArchonAbility1(state));
	}
}
